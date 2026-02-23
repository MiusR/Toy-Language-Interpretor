package org.misu.finalproject.model.statement;


import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.FileNotOpenException;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.StringType;

import java.io.IOException;

public record CloseFileStatement(Expression expression) implements Statement{

    @Override
    public String format() {
        return "close("+expression.format()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        String fileName = StatementUtil.evaluateToString(expression, state.getSymbolTable(), state.getHeapTable());

        try {
            state.getFileTable().closeFile(fileName);
        } catch (IOException e) {
            throw new FileNotOpenException(e.getMessage());
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CloseFileStatement(expression);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        if(!expression.typecheck(typeEnvironment).equals(new StringType()))
            throw new CompilerException("Tried to close file with non string name");
        return typeEnvironment;
    }
}
