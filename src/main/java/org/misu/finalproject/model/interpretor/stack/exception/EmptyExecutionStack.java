package org.misu.finalproject.model.interpretor.stack.exception;

import java.util.EmptyStackException;

public class EmptyExecutionStack extends RuntimeException {
    public EmptyExecutionStack(EmptyStackException e) {
        super(e);
    }
}
