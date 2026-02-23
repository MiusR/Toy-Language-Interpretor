package org.misu.finalproject.model.statement.barrier;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.Statement;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.IntegerType;
import org.misu.finalproject.model.value.IntegerValue;

public record CreateBarrierStatement(String bar, Expression e) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        IntegerValue v = ((IntegerValue) e.evaluate(state.getSymbolTable(), state.getHeapTable()));
        int id = state.getBarrierTable().createBarrier(v.value());
        state.getSymbolTable().updateValue(bar, new IntegerValue(id));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CreateBarrierStatement(bar, e);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        if (!(typeEnvironment.getTypeOfVariable(bar) instanceof  IntegerType) ) {
            throw new CompilerException("Error : tried to save barrier into a non integer type");
        }
        if(!(e.typecheck(typeEnvironment) instanceof IntegerType))
        {
            throw new CompilerException("Error : Barrier id must be integer");
        }
        return typeEnvironment;
    }

    @Override
    public String format() {
        return "bar(" + bar + ", " + e.format() + ")";
    }
}
