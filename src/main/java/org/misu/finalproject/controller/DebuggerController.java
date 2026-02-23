package org.misu.finalproject.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpressionBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.misu.finalproject.controller.exception.ProgramFinishedException;
import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.interpretor.BasicProgramState;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.streams.Out;
import org.misu.finalproject.model.interpretor.table.FileTable;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeEnvironment;
import org.misu.finalproject.model.interpretor.table.barrier.BarrierTable;
import org.misu.finalproject.model.statement.Statement;
import org.misu.finalproject.repository.ConcurrentRepository;
import org.misu.finalproject.view.util.BarrierEntry;
import org.misu.finalproject.view.util.HeapEntry;
import org.misu.finalproject.view.util.SymbolEntry;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DebuggerController {

    private Integer selected = 0;
    private Statement program;
    private Watchdog controller;
    private String logFileName;
    private final ObservableList<String> outItems = FXCollections.observableArrayList();
    private final ObservableList<String> idItems = FXCollections.observableArrayList();
    private final Map<Integer, ObservableList<String>> stackItems = new HashMap<>();
    private final ObservableList<HeapEntry> heapItems = FXCollections.observableArrayList();
    private final Map<Integer, ObservableList<SymbolEntry>> symbolItems = new HashMap<>();
    private final ObservableList<String> fileItems = FXCollections.observableArrayList();
    private final ObservableList<BarrierEntry> barrierItems = FXCollections.observableArrayList();

    @FXML
    private ListView<String> outputView;
    @FXML
    private ListView<String> identifierList;
    @FXML
    private ListView<String> executionStack;
    @FXML
    private TableView<HeapEntry> heapTable;
    @FXML
    private ListView<String> fileTable;
    @FXML
    private TableView<SymbolEntry> symbolTable;
    @FXML
    private TableView<BarrierEntry> barrierTable;

    @FXML
    private void selectProgram() {
        try {
            selected = Integer.parseInt(identifierList.getSelectionModel().getSelectedItems().getFirst());
            executionStack.setItems(stackItems.get(selected));
            symbolTable.setItems(symbolItems.get(selected));
        }catch (NoSuchElementException e) {
            // Do nothing
        }
    }


    public void setProgram(String name, Statement program) {
        this.program = program;
        this.logFileName = name;
        initializeDebugger();
    }

    @FXML
    private void runOneStep(){
        List<ProgramState> programStateList = new ArrayList<>();
        try {
            programStateList = controller.runOneStep();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Runtime Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        updateStacks(programStateList);
    }

    private void updateStacks(List<ProgramState> programStateList) {
        programStateList.forEach(programState -> {
            if(!stackItems.containsKey(programState.getProgramId()))
            {
                stackItems.put(programState.getProgramId(), FXCollections.observableArrayList());
            }
            stackItems.get(programState.getProgramId()).clear();

            stackItems.get(programState.getProgramId()).addAll(
                    programState.getStack().getAll()
                            .stream()
                            .map(Statement::format)
                            .toList().reversed());
        });
    }

    @FXML
    private void runAllStep(){
        List<ProgramState> programStateList = new ArrayList<>();
        try {
            do {
                programStateList = controller.runOneStep();
                updateStacks(programStateList);
            }while (!programStateList.isEmpty());
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Runtime Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private void initializeDebugger() {
        identifierList.setItems(idItems);
        fileTable.setItems(fileItems);
        outputView.setItems(outItems);

        initHeap();
        initSymbol();
        initBarrier();

        heapTable.setItems(heapItems);
        barrierTable.setItems(barrierItems);

        controller = new Watchdog(
                ConcurrentController.newInstance(new ConcurrentRepository(  BasicProgramState.newInstance(program.deepCopy()), logFileName + ".txt")),
                this::heapCallback,
                this::fileCallBack,
                this::outCallback,
                this::symbolCallback,
                this::threadCallback,
                this::barrierCallback
        );
        runOneStep();
    }

    private void initHeap() {
        heapTable.getColumns().clear();

        TableColumn<HeapEntry, Number> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData ->
                cellData.getValue().getIdentifier()
        );

        TableColumn<HeapEntry, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData ->
                cellData.getValue().getValue()
        );

        heapTable.getColumns().addAll(addressColumn, valueColumn);
    }

    private void initSymbol() {
        symbolTable.getColumns().clear();

        TableColumn<SymbolEntry, String> addressColumn = new TableColumn<>("Symbol");
        addressColumn.setCellValueFactory(cellData ->
                cellData.getValue().getIdentifier()
        );

        TableColumn<SymbolEntry, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData ->
                cellData.getValue().getValue()
        );

        symbolTable.getColumns().addAll(addressColumn, valueColumn);
    }

    private void initBarrier() {
        barrierTable.getColumns().clear();

        TableColumn<BarrierEntry, String> addressColumn = new TableColumn<>("Id");
        addressColumn.setCellValueFactory(cellData ->
                cellData.getValue().identifier().asString()
        );

        TableColumn<BarrierEntry, String> valueColumn = new TableColumn<>("Expected");
        valueColumn.setCellValueFactory(cellData ->
                cellData.getValue().value().asString()
        );

        TableColumn<BarrierEntry, String> waitColumn = new TableColumn<>("Waiting");
        waitColumn.setCellValueFactory(cellData ->
                cellData.getValue().waitIds()
        );

        barrierTable.getColumns().addAll(addressColumn, valueColumn, waitColumn);
    }


    private void heapCallback(HeapTable heapTable) {
        heapItems.clear();
        heapItems.addAll(heapTable.getAll().keySet()
                .stream().map(
                key -> new HeapEntry(
                        new SimpleIntegerProperty(key),
                        new SimpleStringProperty(heapTable.memLook(key).get().toString())))
                .collect(Collectors.toSet()));
    }

    private void barrierCallback(BarrierTable barrierTable) {
        barrierItems.clear();
        barrierItems.addAll(barrierTable.getAll().keySet()
                .stream().map(
                        key -> new BarrierEntry(
                                new SimpleIntegerProperty(key),
                                new SimpleIntegerProperty(barrierTable.getAll().get(key).getKey()),
                                new SimpleStringProperty(barrierTable.getAll().get(key).getValue().getAll().stream().
                                        map(Object::toString).
                                        reduce( (x, y) -> x.concat(", ").concat(y)).orElse(""))))
                .collect(Collectors.toSet()));

    }

    private void fileCallBack(FileTable fileTable) {
        fileItems.clear();
        fileItems.addAll(fileTable.getAll().keySet());
    }

    private void outCallback(Out out) {
        outItems.clear();
        outItems.addAll(out.getAll().stream()
                .map(object -> {
                    if(object instanceof Formatable ff)
                        return ff.format();
                    return object.toString();
                })
                .toList());
    }

    private void symbolCallback(Integer id, SymbolTable symbolTable) {
        if(!symbolItems.containsKey(id)) {
            symbolItems.put(id, FXCollections.observableArrayList());
        }
        ObservableList<SymbolEntry> symbolList = symbolItems.get(id);
        symbolList.clear();
        symbolList.addAll(symbolTable.getAll().keySet()
                .stream().map(
                        key -> new SymbolEntry(
                                new SimpleStringProperty(key),
                                new SimpleStringProperty(symbolTable.lookUp(key).toString())))
                .collect(Collectors.toSet()));
    }

    private void threadCallback(Set<Integer> threadIds) {
        idItems.clear();
        idItems.addAll(threadIds.stream().map(Object::toString).collect(Collectors.toSet()));
    }
}

