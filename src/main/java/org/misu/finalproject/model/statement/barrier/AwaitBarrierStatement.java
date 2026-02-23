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

public record AwaitBarrierStatement(String barrier) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        IntegerValue v = (IntegerValue) state.getSymbolTable().lookUp(barrier);
        int barrierCount = state.getBarrierTable().await(v.value(), state.getProgramId());
        if(barrierCount == -1) {
            throw new StatementExecutionException("Error : Called await on a null barrier");
        }

        if(barrierCount < state.getBarrierTable().getCount(v.value())) {
            state.getStack().pushStatement(this.deepCopy());
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AwaitBarrierStatement(barrier);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        if(!(typeEnvironment.getTypeOfVariable(barrier) instanceof IntegerType))
        {
            throw new CompilerException("Error : Barrier id must be integer");
        }
        return typeEnvironment;
    }

    @Override
    public String format() {
        return "await(" + barrier + ")";
    }
}
