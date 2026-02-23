package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.RefType;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.RefValue;
import org.misu.finalproject.model.value.Value;

public record MallocStatement(String varName, Expression expression) implements Statement{
    @Override
    public String format() {
        return "malloc(" + varName + ", "+expression.format() +")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        if (!state.getSymbolTable().isDefined(varName))
            throw new StatementExecutionException("ERROR : " + varName + " is undefined.");
        Value val = expression.evaluate(state.getSymbolTable(), state.getHeapTable());
        Value refMaybe = state.getSymbolTable().lookUp(varName);
        if(!(refMaybe instanceof RefValue))
            throw new StatementExecutionException("ERROR : Can not allocate memory to non pointer type.");
        RefValue ref = (RefValue) refMaybe;
        if (!val.getType().equals(((RefType)ref.getType()).inner()))
            throw new StatementExecutionException("ERROR : Type mismatch between reference and value.");
        Integer address = state.getHeapTable().memSet(val);
        state.getSymbolTable().updateValue(varName, new RefValue(address, val.getType()));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new MallocStatement(varName, expression);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        Type typevar = typeEnvironment.getTypeOfVariable(varName);
        Type typeexp = expression.typecheck(typeEnvironment);

        if (typevar.equals(new RefType(typeexp)))
            return typeEnvironment;
        throw new CompilerException("Tried to bind allocated memory to non Ref value.");
    }
}
