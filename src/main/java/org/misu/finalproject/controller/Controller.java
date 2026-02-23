package org.misu.finalproject.controller;

import org.misu.finalproject.controller.exception.ProgramFinishedException;
import org.misu.finalproject.model.interpretor.ProgramState;

import java.io.IOException;
import java.util.List;

public interface Controller {

    ProgramState oneStep(ProgramState state, boolean log) throws ProgramFinishedException, IOException;
    List<ProgramState> allStep(boolean log) throws IOException, InterruptedException;
    void oneStepForAll(List<ProgramState> programStateList) throws IOException, InterruptedException;
    List<ProgramState> oneStepForAll() throws IOException, InterruptedException;
    String displayState(ProgramState state);
    List<ProgramState> removeCompletedProgram(List<ProgramState> programStateList);
}
