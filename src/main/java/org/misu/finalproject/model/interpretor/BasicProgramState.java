package org.misu.finalproject.model.interpretor;


import org.misu.finalproject.controller.exception.ProgramFinishedException;
import org.misu.finalproject.model.interpretor.stack.BasicExecutionStack;
import org.misu.finalproject.model.interpretor.stack.ExecutionStack;
import org.misu.finalproject.model.interpretor.streams.BasicOut;
import org.misu.finalproject.model.interpretor.streams.Out;
import org.misu.finalproject.model.interpretor.table.*;
import org.misu.finalproject.model.interpretor.table.barrier.BarrierTable;
import org.misu.finalproject.model.interpretor.table.barrier.MapBarrierTable;
import org.misu.finalproject.model.statement.CompoundStatement;
import org.misu.finalproject.model.statement.Statement;

public record BasicProgramState(ExecutionStack executionStack, SymbolTable symbolTable, Out out, FileTable table, HeapTable heapTable, BarrierTable barrierTable, Statement originalProgram, int id) implements ProgramState{
    private static int nextID = 0;

    public static BasicProgramState newInstance(ExecutionStack executionStack, SymbolTable symbolTable, Out out, FileTable table, HeapTable heapTable, BarrierTable barrierTable, Statement originalProgram) {
        return new BasicProgramState(executionStack, symbolTable, out, table, heapTable, barrierTable,originalProgram, generateNextId());
    }

    public static BasicProgramState newInstance(Statement originalProgram) {
        ExecutionStack stack = new BasicExecutionStack();
        stack.pushStatement(originalProgram);
        return new BasicProgramState(stack, new BasicSymbolTable(), new BasicOut(), new MapFileTable(), new MapHeapTable(), new MapBarrierTable(), originalProgram, generateNextId());
    }

    private static synchronized int generateNextId() {
        return nextID++;
    }

    @Override
    public ExecutionStack getStack() {
        return executionStack;
    }

    @Override
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public Out getOut() {
        return out;
    }

    @Override
    public FileTable getFileTable() {
        return table;
    }

    @Override
    public HeapTable getHeapTable() {
        return heapTable;
    }

    @Override
    public Integer getProgramId() {
        return id;
    }

    @Override
    public Boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    @Override
    public ProgramState oneStep() throws ProgramFinishedException {
        if(!isNotCompleted())
            throw new ProgramFinishedException();
        Statement potential = executionStack.popStatement();
        if(potential instanceof CompoundStatement) {
            potential.execute(this);
            return executionStack.popStatement().execute(this);
        }
        return potential.execute(this);
    }

    @Override
    public BarrierTable getBarrierTable() {
        return barrierTable;
    }
}
