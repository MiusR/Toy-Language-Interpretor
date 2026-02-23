package org.misu.finalproject.model.statement.exception;

public class FileNotOpenException extends StatementExecutionException {
    public FileNotOpenException(String s) {
        super(s);
    }

    public FileNotOpenException(RuntimeException e) {
        super(e);
    }
}
