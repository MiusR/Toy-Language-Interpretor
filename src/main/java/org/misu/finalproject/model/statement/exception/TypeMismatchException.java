package org.misu.finalproject.model.statement.exception;

public class TypeMismatchException extends StatementExecutionException {
    public TypeMismatchException(String s) {
        super(s);
    }
    public TypeMismatchException(RuntimeException e) {
        super(e);
    }
}
