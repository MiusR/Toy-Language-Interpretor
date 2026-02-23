package org.misu.finalproject.model.interpretor.table.exception;

public class MemoryViolationException extends RuntimeException {
    public MemoryViolationException(String accessedNull) {
        super(accessedNull);
    }
}
