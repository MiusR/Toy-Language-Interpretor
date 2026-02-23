package org.misu.finalproject.model.statement.exception;

public class FileDoesNotExistException extends StatementExecutionException {
    public FileDoesNotExistException(String s) {
        super(s);
    }

    public FileDoesNotExistException(RuntimeException e) {
        super(e);
    }
}
