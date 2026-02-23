package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.FileAlreadyOpenException;
import org.misu.finalproject.model.statement.exception.FileDoesNotExistException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.StringType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public record OpenFileStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        String fileName = StatementUtil.evaluateToString(expression, state.getSymbolTable(), state.getHeapTable());

        if(state.getFileTable().isOpen(fileName)) {
            throw new FileAlreadyOpenException("ERROR : File with name " + fileName + " is already opened!");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            state.getFileTable().addFile(fileName, reader);
        }catch (FileNotFoundException e) {
            throw new FileDoesNotExistException("ERROR : FIle with name " + fileName + " does not exist!");
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new OpenFileStatement(expression);
    }

    @Override
    public String format() {
        return "open(" + expression.format()+")";
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        if(!expression.typecheck(typeEnvironment).equals(new StringType()))
            throw new CompilerException("Tried to open file with non string name");
        return typeEnvironment;
    }
}
