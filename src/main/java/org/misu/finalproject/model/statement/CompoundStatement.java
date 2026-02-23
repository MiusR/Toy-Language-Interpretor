package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.stack.ExecutionStack;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;

public record CompoundStatement(Statement firstStatement, Statement secondStatement) implements Statement {


    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException {
        ExecutionStack eStack = state.getStack();
        eStack.pushStatement(secondStatement);
        eStack.pushStatement(firstStatement);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder();
        builder.append(firstStatement.format()).append("\n");
        builder.append(secondStatement.format());
        return builder.toString();
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        return secondStatement.typecheck(firstStatement.typecheck(typeEnvironment));
    }
}
