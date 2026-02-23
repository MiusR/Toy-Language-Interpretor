package org.misu.finalproject.controller;

import org.misu.finalproject.controller.exception.ControllerException;
import org.misu.finalproject.controller.exception.ProgramFinishedException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.repository.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public record ConcurrentController(Repository repo, ExecutorService executorService) implements Controller {


    public static ConcurrentController newInstance(Repository repo) {
        return new ConcurrentController(repo, Executors.newFixedThreadPool(2));
    }

    @Override
    public ProgramState oneStep(ProgramState state, boolean log) throws ProgramFinishedException, IOException {
        return state.oneStep();
    }

    @Override
    public List<ProgramState> allStep(boolean log) throws IOException, InterruptedException {
        List<ProgramState> states = removeCompletedProgram(repo.getProgramList());
        while (!states.isEmpty()) {
            GarbageCollector.collectAll(states.stream().map(ProgramState::getSymbolTable).toList(), states.getFirst().getHeapTable());
            oneStepForAll(states);
            states = removeCompletedProgram(repo.getProgramList());
        }
        executorService.shutdownNow();
        repo.setProgramList(states);
        return states;
    }

    @Override
    public void oneStepForAll(List<ProgramState> programStateList) throws IOException, InterruptedException {
        List<ProgramState> newList = new LinkedList<>(programStateList);
        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState programState) -> (Callable<ProgramState>)(programState::oneStep))
                .toList();

        List<ProgramState> newProgramList = executorService.invokeAll(callList).stream()
                .map(programStateFuture -> {
                    try {
                        return programStateFuture.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new ControllerException(e.getMessage());
                    }
                }).filter(Objects::nonNull).toList();

        newList.addAll(newProgramList);

        for (ProgramState state : newList) {
            repo.logProgramState(state);
        }

        repo.setProgramList(newList);
    }

    @Override
    public List<ProgramState> oneStepForAll() throws IOException, InterruptedException {
        List<ProgramState> states = removeCompletedProgram(repo.getProgramList());
        if(states.isEmpty())
            return Collections.emptyList();

        GarbageCollector.collectAll(states.stream().map(ProgramState::getSymbolTable).toList(), states.getFirst().getHeapTable());
        oneStepForAll(states);
        List<ProgramState> oldState = new LinkedList<>(states);
        states = removeCompletedProgram(repo.getProgramList());

        if(states.isEmpty())
            executorService.shutdownNow();

        repo.setProgramList(states);
        return oldState;
    }

    @Override
    public String displayState(ProgramState state) {
        String output = state.getOut().format();
        state.getOut().clear();
        return output;
    }

    @Override
    public List<ProgramState> removeCompletedProgram(List<ProgramState> programStateList) {
        return programStateList.stream().filter(ProgramState::isNotCompleted).toList();
    }
}
