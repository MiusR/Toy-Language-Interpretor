package org.misu.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.misu.finalproject.controller.exception.ProgramFinishedException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.streams.Out;
import org.misu.finalproject.model.interpretor.table.*;
import org.misu.finalproject.model.interpretor.table.barrier.BarrierTable;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Watchdog {

    private final Controller subject;
    private final ObservableMap<Integer, Integer> lastSymbolTables = FXCollections.observableHashMap();
    private int lastOutHash = -1;
    private int lastHeapHash = -1;
    private int lastFileHash = -1;
    private int lastBarrierHash = -1;

    private final Consumer<HeapTable> heapCallback;
    private final Consumer<FileTable> fileCallback;
    private final Consumer<Out> outCallback;
    private final BiConsumer<Integer, SymbolTable> symbolCallback;
    private final Consumer<Set<Integer>> threadCallback;
    private final Consumer<BarrierTable> barrierCallback;

    public Watchdog(Controller subject, Consumer<HeapTable> heapCallback, Consumer<FileTable> fileCallback, Consumer<Out> outCallback, BiConsumer<Integer, SymbolTable> symbolCallback, Consumer<Set<Integer>> threadCallback, Consumer<BarrierTable> barrierCallback) {
        this.subject = subject;
        this.heapCallback = heapCallback;
        this.fileCallback = fileCallback;
        this.outCallback = outCallback;
        this.symbolCallback = symbolCallback;
        this.threadCallback = threadCallback;
        this.barrierCallback = barrierCallback;
    }


    List<ProgramState> runOneStep() throws IOException, InterruptedException, ProgramFinishedException {
        List<ProgramState> states = subject.oneStepForAll();

        if(states.isEmpty()) {
            heapCallback.accept(new MapHeapTable());
            fileCallback.accept(new MapFileTable());
            threadCallback.accept(Collections.emptySet());
            return Collections.emptyList();
        }
        // Check for modifications
        int newHeapHash = states.getFirst().getHeapTable().getAll().hashCode();
        if(newHeapHash != lastHeapHash) {
            lastHeapHash = newHeapHash;
            heapCallback.accept(states.getFirst().getHeapTable());
        }

        int newFileHash = states.getFirst().getFileTable().getAll().hashCode();
        if(newFileHash != lastFileHash) {
            lastFileHash = newFileHash;
            fileCallback.accept(states.getFirst().getFileTable());
        }

        int newOutHash = states.getFirst().getOut().getAll().hashCode();
        if(newOutHash != lastOutHash) {
            lastOutHash = newOutHash;
            outCallback.accept(states.getFirst().getOut());
        }

        int newBarrierHash = states.getFirst().getBarrierTable().getAll().hashCode();
        if(newBarrierHash != lastBarrierHash) {
            lastBarrierHash = newBarrierHash;
            barrierCallback.accept(states.getFirst().getBarrierTable());
        }


        Set<Integer> existingPrograms = new HashSet<>();

        states.forEach(programState -> {
            int id = programState.getProgramId();
            existingPrograms.add(id);
            putIfAbsent(programState);
            checkSymbolTable(programState);
        });

        removeFinishedPrograms(existingPrograms);
        return states;
    }

    private void putIfAbsent(ProgramState programState) {
        if(!lastSymbolTables.containsKey(programState.getProgramId())){
            lastSymbolTables.put(programState.getProgramId(), programState.getSymbolTable().hashCode());
        }
    }

    private void removeFinishedPrograms(Set<Integer> existingPrograms) {
        // Erase old watched programs
        Set<Integer> storedPrograms = new HashSet<>(lastSymbolTables.keySet());
        storedPrograms.removeAll(existingPrograms);
        storedPrograms.forEach(lastSymbolTables::remove);

        threadCallback.accept(lastSymbolTables.keySet());
    }

    private void checkSymbolTable(ProgramState programState) {
        if(lastSymbolTables.get(programState.getProgramId()) != programState.getSymbolTable().getAll().hashCode()) {
            lastSymbolTables.put(programState.getProgramId(), programState.getSymbolTable().getAll().hashCode());
            symbolCallback.accept(programState.getProgramId(), programState.getSymbolTable());
        }
    }
}
