package org.misu.finalproject.model.interpretor.table.exception;

public class SymbolLookupException extends RuntimeException {

    public SymbolLookupException(String s) {
        super(s);
    }

    public SymbolLookupException(RuntimeException r) {
        super(r);
    }
}
