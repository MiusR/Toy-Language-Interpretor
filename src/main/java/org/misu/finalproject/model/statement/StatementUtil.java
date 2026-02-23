package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.value.StringValue;

public class StatementUtil {

    public static String evaluateToString(Expression expression, SymbolTable table, HeapTable heapTable) throws InvalidTypeException {
        if(!(expression.evaluate(table, heapTable)
                instanceof StringValue(String stringValue))) {
            throw new InvalidTypeException("ERROR : Files can only be provided via path.");
        }

        return stringValue;
    }
}
