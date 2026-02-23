package org.misu.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.misu.finalproject.model.expresion.*;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.interpretor.table.TypeEnvironment;
import org.misu.finalproject.model.statement.*;
import org.misu.finalproject.model.statement.barrier.AwaitBarrierStatement;
import org.misu.finalproject.model.statement.barrier.CreateBarrierStatement;
import org.misu.finalproject.model.type.*;
import org.misu.finalproject.model.value.BooleanValue;
import org.misu.finalproject.model.value.IntegerValue;
import org.misu.finalproject.model.value.StringValue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectionController implements Initializable {
    @FXML
    private ListView<String> exampleList;
    private final ObservableList<String> examples = FXCollections.observableArrayList();
    private final ObservableMap<String, Statement> nameToExample = FXCollections.observableHashMap();

    @FXML
    private void runExample(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            exampleList.getSelectionModel().getSelectedItems().forEach(selected -> {
                Statement s = nameToExample.get(selected);
                try {
                    FXMLLoader loader = new FXMLLoader(SelectionController.class.getResource("/org/misu/finalproject/debugger-view.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle(selected);

                    Parent root = loader.load();
                    DebuggerController controller = loader.getController();
                    controller.setProgram("lastLog", s);

                    stage.setScene(new Scene(root, 800, 600));
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Got here with error");
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exampleList.setItems(examples);

        Statement s = loadProgramOne();
        registerExample(s.format(), s);

        s = loadProgramTwo();
        registerExample(s.format(), s);

        s = loadProgramThree();
        registerExample(s.format(), s);

        s = loadProgramFour();
        registerExample(s.format(), s);

        s = loadProgramFive();
        registerExample(s.format(), s);

        s = loadProgramSix();
        registerExample(s.format(), s);

        s = loadProgramSeven();
        registerExample(s.format(), s);

        s = loadProgramEight();
        registerExample(s.format(), s);

        s = loadProgramNine();
        registerExample(s.format(), s);

        s = loadProgramTen();
        registerExample(s.format(), s);

        s = loadProgramEleven();
        registerExample(s.format(), s);

        s = loadProgramTwelve();
        registerExample(s.format(), s);

        s = loadProgramThirteen();
        registerExample(s.format(), s);

        s = loadProgramFourteen();
        registerExample(s.format(), s);
    }

    private void registerExample(String name, Statement program) {
        try {
            program.typecheck(new TypeEnvironment());
            nameToExample.put(name, program);
            examples.add(name);
        } catch (CompilerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Compile error: " + name);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private static Statement loadProgramFourteen() {
        return new CompoundStatement(
                new CompoundStatement(
                        new SymbolDeclarationStatement("v1", new RefType(new IntegerType())),
                        new CompoundStatement(
                                new SymbolDeclarationStatement("v2", new RefType(new IntegerType())),
                                new CompoundStatement(
                                        new SymbolDeclarationStatement("v3", new RefType(new IntegerType())),
                                        new CompoundStatement(
                                                new SymbolDeclarationStatement("cnt", new IntegerType()),
                                                new CompoundStatement(
                                                        new MallocStatement("v1", new ConstantExpression(new IntegerValue(2))),
                                                        new CompoundStatement(
                                                                new MallocStatement("v2", new ConstantExpression(new IntegerValue(3))),
                                                                new CompoundStatement(
                                                                        new MallocStatement("v3", new ConstantExpression(new IntegerValue(4))),
                                                                        new CompoundStatement(
                                                                                new CreateBarrierStatement("cnt", new MemoryExpression(new VariableExpression("v2"))),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(new CompoundStatement(
                                                                                           new AwaitBarrierStatement("cnt"),
                                                                                                new CompoundStatement(
                                                                                                        new HeapAssignmentStatement("v1", new BinaryOperatorExpression(
                                                                                                                new MemoryExpression(new VariableExpression("v1")),
                                                                                                                new ConstantExpression(new IntegerValue(10)),
                                                                                                                Operator.MULTIPLICATION)),
                                                                                                                new PrintStatement(new MemoryExpression(new VariableExpression("v1")))
                                                                                                )
                                                                                        )),
                                                                                    new CompoundStatement(
                                                                                            new ForkStatement(new CompoundStatement(
                                                                                                    new AwaitBarrierStatement("cnt"),
                                                                                                    new CompoundStatement(
                                                                                                            new HeapAssignmentStatement("v2", new BinaryOperatorExpression(
                                                                                                                    new MemoryExpression(new VariableExpression("v2")),
                                                                                                                    new ConstantExpression(new IntegerValue(10)),
                                                                                                                    Operator.MULTIPLICATION)),
                                                                                                            new CompoundStatement(
                                                                                                                    new HeapAssignmentStatement("v2", new BinaryOperatorExpression(
                                                                                                                            new MemoryExpression(new VariableExpression("v2")),
                                                                                                                            new ConstantExpression(new IntegerValue(10)),
                                                                                                                            Operator.MULTIPLICATION)),
                                                                                                                    new PrintStatement(new MemoryExpression(new VariableExpression("v2")))
                                                                                                            )
                                                                                                    )
                                                                                            )),
                                                                                            new CompoundStatement(
                                                                                                    new AwaitBarrierStatement("cnt"),
                                                                                                    new PrintStatement(new MemoryExpression(new VariableExpression("v3")))
                                                                                            )

                                                                                    )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ),
                new NopStatement()
        );
    }

    private static Statement loadProgramThirteen() {
        return new CompoundStatement(
                new CompoundStatement(
                        new SymbolDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(
                                new SymbolDeclarationStatement("a", new RefType(new IntegerType())),
                                new CompoundStatement(new SymbolDeclarationStatement("b", new RefType(new IntegerType())),
                                        new CompoundStatement(
                                                new MallocStatement("a", new ConstantExpression(new IntegerValue(0))),
                                                new CompoundStatement(
                                                        new MallocStatement("b", new ConstantExpression(new IntegerValue(0))),
                                                        new CompoundStatement(
                                                                new HeapAssignmentStatement("a", new ConstantExpression(new IntegerValue(1))),
                                                                new CompoundStatement(
                                                                        new HeapAssignmentStatement("b",new ConstantExpression(new IntegerValue(2))),
                                                                        new CompoundStatement(
                                                                                new TurnaryStatement("v",
                                                                                        new BinaryOperatorExpression(
                                                                                                new MemoryExpression(new VariableExpression("a")),
                                                                                                new MemoryExpression(new VariableExpression("b")),
                                                                                                Operator.LESSER
                                                                                        ),
                                                                                        new ConstantExpression(new IntegerValue(100)),
                                                                                        new ConstantExpression(new IntegerValue(200))),
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(new VariableExpression("v")),
                                                                                        new CompoundStatement(
                                                                                                new TurnaryStatement("v",
                                                                                                        new BinaryOperatorExpression(
                                                                                                                new BinaryOperatorExpression( new MemoryExpression(new VariableExpression("b")), new ConstantExpression(new IntegerValue(2)), Operator.SUBTRACTION),
                                                                                                                new MemoryExpression(new VariableExpression("a")),
                                                                                                                Operator.GREATER
                                                                                                        ),
                                                                                                        new ConstantExpression(new IntegerValue(100)),
                                                                                                        new ConstantExpression(new IntegerValue(200))),
                                                                                                new PrintStatement(new VariableExpression("v"))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        ))
                        )
                ),
                new NopStatement()
        );
    }

    private static Statement loadProgramTwelve() {
        return new CompoundStatement(
                new CompoundStatement(
                        new SymbolDeclarationStatement("a", new RefType(new IntegerType())),
                        new CompoundStatement(
                                new MallocStatement("a", new ConstantExpression(new IntegerValue(20))),
                                new CompoundStatement(
                                        new ForStatement(
                                          new ConstantExpression(new IntegerValue(0)),
                                          new ConstantExpression(new IntegerValue(3)),
                                          new BinaryOperatorExpression(new VariableExpression("v"), new ConstantExpression(new IntegerValue(1)), Operator.ADDITION),
                                          new ForkStatement(
                                                  new CompoundStatement(
                                                          new PrintStatement(new VariableExpression("v")),
                                                          new AssignmentStatement("v",
                                                                  new BinaryOperatorExpression(
                                                                          new VariableExpression("v"),
                                                                          new MemoryExpression(new VariableExpression("a")),
                                                                          Operator.MULTIPLICATION
                                                                  ))
                                                  )
                                          )
                                        ),
                                        new PrintStatement(new MemoryExpression(new VariableExpression("a")))
                                )
                        )
                ),
                new NopStatement()
        );
    }

    private static Statement loadProgramEleven() {
        return new CompoundStatement(
                new CompoundStatement(
                        new SymbolDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(new SymbolDeclarationStatement("x", new IntegerType()),
                                new CompoundStatement(
                                        new SymbolDeclarationStatement("y", new IntegerType()),
                                        new CompoundStatement(
                                                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(0))),
                                                new CompoundStatement(
                                                        new RepeatUntilStatement(
                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                                new AssignmentStatement("v",
                                                                                        new BinaryOperatorExpression(new VariableExpression("v"),
                                                                                                new ConstantExpression(new IntegerValue(1)), Operator.SUBTRACTION))

                                                                )),
                                                                        new AssignmentStatement("v", new BinaryOperatorExpression(new VariableExpression("v"),
                                                                                new ConstantExpression(new IntegerValue(1)), Operator.ADDITION))),
                                                                new BinaryOperatorExpression(new VariableExpression("v"), new ConstantExpression(new IntegerValue(3)), Operator.EQUAL)
                                                        ),
                                                        new CompoundStatement(
                                                                new AssignmentStatement("x", new ConstantExpression(new IntegerValue(1))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("y", new ConstantExpression(new IntegerValue(3))),
                                                                        new PrintStatement(new BinaryOperatorExpression(new VariableExpression("v"), new ConstantExpression(new IntegerValue(10)),Operator.MULTIPLICATION))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ), new NopStatement());
    }


    private static Statement loadProgramOne() {
        return new CompoundStatement(new CompoundStatement(new SymbolDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v")))), new NopStatement());
    }

    private static Statement loadProgramTwo() {
        return new CompoundStatement(new CompoundStatement(new SymbolDeclarationStatement("a", new IntegerType()),
                new CompoundStatement(new SymbolDeclarationStatement("b", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new BinaryOperatorExpression(
                                new ConstantExpression(new IntegerValue(2)),
                                new BinaryOperatorExpression(new ConstantExpression(new IntegerValue(3)),
                                        new ConstantExpression(new IntegerValue(5)), Operator.MULTIPLICATION), Operator.ADDITION)),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new BinaryOperatorExpression(new VariableExpression("a"), new ConstantExpression(new IntegerValue(1)), Operator.ADDITION)),
                                        new PrintStatement(new VariableExpression("b")))))), new NopStatement());
    }

    private static Statement loadProgramThree() {
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("c", new BooleanType()),
                new CompoundStatement(new SymbolDeclarationStatement("x", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("c", new ConstantExpression(new BooleanValue(true))),
                                new CompoundStatement(new ConditionalStatement(new VariableExpression("c"), new AssignmentStatement("x", new ConstantExpression(new IntegerValue(2))), new AssignmentStatement("x", new ConstantExpression(new IntegerValue(3)))), new PrintStatement(new VariableExpression("x")))))), new NopStatement());
    }


    private static Statement loadProgramFour() {
        return new CompoundStatement(new CompoundStatement(new SymbolDeclarationStatement("varf", new StringType()), new CompoundStatement(new AssignmentStatement("varf", new ConstantExpression(new StringValue("test.in"))), new CompoundStatement(new OpenFileStatement(new VariableExpression("varf")), new CompoundStatement(new SymbolDeclarationStatement("varc", new IntegerType()), new CompoundStatement(new ReadFromFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CompoundStatement(new ReadFromFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CloseFileStatement(new VariableExpression("varf")))))))))), new NopStatement());
    }

    private static Statement loadProgramFive(){
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new MallocStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new SymbolDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new MallocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        ), new NopStatement());
    }

    private static Statement loadProgramSix(){
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new MallocStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new SymbolDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new MallocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new MemoryExpression(new VariableExpression("v"))),
                                                new PrintStatement(new BinaryOperatorExpression(
                                                        new MemoryExpression(new MemoryExpression(new VariableExpression("a"))),
                                                        new ConstantExpression(new IntegerValue(5)), Operator.ADDITION
                                                ))
                                        )
                                )
                        )
                )
        ), new NopStatement());
    }

    private static Statement loadProgramSeven() {
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new MallocStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new MemoryExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapAssignmentStatement("v", new ConstantExpression(new IntegerValue(30))),
                                        new PrintStatement(new BinaryOperatorExpression(
                                                new MemoryExpression(new VariableExpression("v")),
                                                new ConstantExpression(new IntegerValue(5)), Operator.ADDITION
                                        ))
                                )
                        )
                )
        ), new NopStatement());
    }

    private static Statement loadProgramEight(){
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new MallocStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new SymbolDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new MallocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new MallocStatement("v", new ConstantExpression(new IntegerValue(30))),
                                                new PrintStatement(new MemoryExpression(new MemoryExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        ), new NopStatement());
    }

    private static Statement loadProgramNine() {
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(new IntegerValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new BinaryOperatorExpression(new VariableExpression("v"), new ConstantExpression(new IntegerValue(0)), Operator.GREATER),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new BinaryOperatorExpression(new VariableExpression("v"), new ConstantExpression(new IntegerValue(1)), Operator.SUBTRACTION))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        ), new NopStatement());
    }

    private static Statement loadProgramTen() {
        return new CompoundStatement(new CompoundStatement(
                new SymbolDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new SymbolDeclarationStatement("a", new RefType(new IntegerType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                        new MallocStatement("a", new ConstantExpression(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new HeapAssignmentStatement("a", new ConstantExpression(new IntegerValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ConstantExpression(new IntegerValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new MemoryExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new MemoryExpression(new VariableExpression("a"))))))))), new NopStatement());
    }
}
