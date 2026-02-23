package org.misu.finalproject.model.expresion;

import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.type.RefType;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.RefValue;
import org.misu.finalproject.model.value.Value;

import java.util.Optional;


public record MemoryExpression(Expression expression) implements Expression {

    @Override
    public String toString() {
        return "(*"+expression.format() + ")";
    }

    @Override
    public Value evaluate(SymbolTable table, HeapTable heapTable) throws ExpressionEvaluationException {
        Value refMaybe = expression.evaluate(table, heapTable);
        if(refMaybe instanceof RefValue ref) {
            Optional<Value> val = heapTable.memLook(ref.address());
            if(val.isEmpty()) {
                throw new ExpressionEvaluationException("ERROR : Memory Violation");
            }
            return val.get();
        }else
            throw new ExpressionEvaluationException("ERROR : Could not get heap value from " + refMaybe.getType().toString());
    }

    @Override
    public Type typecheck(TypeTable environment) throws CompilerException {
        Type type = expression.typecheck(environment);
        if(type instanceof RefType(Type inner))
            return inner;
        throw new CompilerException("Argument not of type reference.");
    }
}

