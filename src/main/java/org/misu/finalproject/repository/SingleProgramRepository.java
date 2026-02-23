package org.misu.finalproject.repository;


import org.misu.finalproject.model.interpretor.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SingleProgramRepository implements Repository{
    private ProgramState state;
    private final String logFilePath;
    public SingleProgramRepository(ProgramState state, String logFilePath) {
        this.state = state;
        this.logFilePath = logFilePath;
    }


    @Override
    public void setProgramState(ProgramState state) {
        this.state = state;
    }

    @Override
    public void logProgramState(ProgramState state) throws IOException {
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            writer.format("\n==== " + state.getProgramId() +" ====\n");
            writer.format(state.getStack().format());
            writer.format(state.getSymbolTable().format());
            writer.format(state.getHeapTable().format());
            writer.format(state.getOut().format());
            writer.format(state.getFileTable().format());
            writer.format("\n\n");
        };

    }

    @Override
    public List<ProgramState> getProgramList() {
        return List.of(state);
    }

    @Override
    public void setProgramList(List<ProgramState> programList) {
        if(!programList.isEmpty())
            this.state = programList.getFirst();
    }


}
