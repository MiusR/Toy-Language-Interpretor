package org.misu.finalproject.model.statement.exception;

public class StatementExecutionException extends RuntimeException {
    public StatementExecutionException(String s){
        super(s);
    }

    public StatementExecutionException(RuntimeException e) {
        super(e);
    }
}
