package org.misu.finalproject.model.statement.exception;

public class InvalidTypeException extends StatementExecutionException {
    public InvalidTypeException(String message) {
        super(message);
    }

    public InvalidTypeException(RuntimeException e) {
        super(e);
    }
}
