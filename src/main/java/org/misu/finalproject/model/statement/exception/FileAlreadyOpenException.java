package org.misu.finalproject.model.statement.exception;

public class FileAlreadyOpenException extends StatementExecutionException {
    public FileAlreadyOpenException(String s) {
        super(s);
    }
    public FileAlreadyOpenException(RuntimeException e ) {
        super(e);
    }
}
