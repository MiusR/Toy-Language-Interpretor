package org.misu.finalproject.repository;


import org.misu.finalproject.model.interpretor.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class ConcurrentRepository implements  Repository{
    private List<ProgramState> programsStates;
    private final String logPrefix;

    public ConcurrentRepository(ProgramState programsStates, String logPrefix) {
        this.programsStates = new LinkedList<>();
        this.programsStates.add(programsStates);
        this.logPrefix = logPrefix;
    }

    @Override
    public void setProgramState(ProgramState state) {
        programsStates.addFirst(state);
    }

    @Override
    public void logProgramState(ProgramState state) throws IOException {
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logPrefix,true)))) {
            writer.format("\n==== " + state.getProgramId() + " ====\n");
            writer.format(state.getStack().format());
            writer.format(state.getSymbolTable().format());
            writer.format(state.getHeapTable().format());
            writer.format(state.getOut().format());
            writer.format(state.getFileTable().format());
            writer.format(state.getBarrierTable().format());
            writer.format("\n\n");
        };
    }


    @Override
    public List<ProgramState> getProgramList() {
        return programsStates;
    }

    @Override
    public void setProgramList(List<ProgramState> programList) {
        this.programsStates = programList;
    }
}
