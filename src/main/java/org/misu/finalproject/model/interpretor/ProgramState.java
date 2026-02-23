package org.misu.finalproject.model.interpretor;


import org.misu.finalproject.controller.exception.ProgramFinishedException;
import org.misu.finalproject.model.interpretor.stack.ExecutionStack;
import org.misu.finalproject.model.interpretor.streams.Out;
import org.misu.finalproject.model.interpretor.table.barrier.BarrierTable;
import org.misu.finalproject.model.interpretor.table.FileTable;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;

public interface ProgramState {

    ExecutionStack getStack();

    SymbolTable getSymbolTable();

    Out getOut();

    FileTable getFileTable();

    HeapTable getHeapTable();

    Integer getProgramId();

    Boolean isNotCompleted();

    ProgramState oneStep() throws ProgramFinishedException;

    BarrierTable getBarrierTable();
}
