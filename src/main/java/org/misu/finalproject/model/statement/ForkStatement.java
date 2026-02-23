package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.BasicProgramState;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.stack.BasicExecutionStack;
import org.misu.finalproject.model.interpretor.stack.ExecutionStack;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;

public record ForkStatement(Statement statement) implements Statement{

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        ExecutionStack executionStack = new BasicExecutionStack();
        executionStack.pushStatement(statement);
        SymbolTable symbolTableClone = state.getSymbolTable().deepClone();

        return BasicProgramState.newInstance(
                executionStack, symbolTableClone,
                state.getOut(), state.getFileTable(),
                state.getHeapTable(), state.getBarrierTable(), statement);
    }

    @Override
    public String format() {
        return "fork(" + statement.format()+ ")";
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        statement.typecheck(typeEnvironment.clone());
        return typeEnvironment;
    }
}
