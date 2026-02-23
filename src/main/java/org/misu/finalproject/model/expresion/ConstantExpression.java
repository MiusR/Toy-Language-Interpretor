package org.misu.finalproject.model.expresion;

import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.Value;

public record ConstantExpression(Value result) implements Expression {

    @Override
    public Value evaluate(SymbolTable table, HeapTable heapTable) throws ExpressionEvaluationException {
        return result;
    }

    @Override
    public Type typecheck(TypeTable environment) throws CompilerException {
        return result.getType();
    }

    @Override
    public String toString() {
        return format();
    }

    @Override
    public String format() {
        return result.format();
    }
}
