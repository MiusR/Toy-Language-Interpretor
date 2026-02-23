package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.interpretor.table.exception.MemoryViolationException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.RefType;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.RefValue;
import org.misu.finalproject.model.value.Value;

public record HeapAssignmentStatement(String symbol, Expression exp) implements Statement {

    @Override
    public String format() {
        return "*"+symbol + "=" + exp.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        SymbolTable table = state.getSymbolTable();

        try {
        Value val = exp.evaluate(table, state.getHeapTable());
        if(table.isDefined(symbol) &&
                table.lookUp(symbol) instanceof RefValue refValue
                && val.getType().equals(refValue.locationType())) {
            state.getHeapTable().memUpdate(refValue.address(), val);
        }
        } catch (MemoryViolationException e) {
            throw new StatementExecutionException(e);
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new HeapAssignmentStatement(symbol, exp);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        Type pointerType = exp.typecheck(typeEnvironment);
        if (!typeEnvironment.getTypeOfVariable(symbol).equals(new RefType(pointerType)))
            throw new CompilerException("Tried to assign heap memory to wrong type of reference.");

        return typeEnvironment;
    }
}
