package org.misu.finalproject.repository;

import org.misu.finalproject.model.interpretor.ProgramState;

import java.io.IOException;
import java.util.List;

public interface Repository {

    void setProgramState(ProgramState state);
    void logProgramState(ProgramState programState) throws IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programList);
}
