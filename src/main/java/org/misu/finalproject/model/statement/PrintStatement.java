package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;

public record PrintStatement(Expression exp) implements Statement {

    @Override
    public String toString() {
        return "print(" +
                exp.format() +
                ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        state.getOut().add(exp.evaluate(state.getSymbolTable(), state.getHeapTable()));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(exp);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        exp.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
