package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;

public class NopStatement implements Statement{
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        // Do nothing :)
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String format() {
        return "";
    }
}
