package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.FileNotOpenException;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.statement.exception.TypeMismatchException;
import org.misu.finalproject.model.type.IntegerType;
import org.misu.finalproject.model.type.StringType;
import org.misu.finalproject.model.value.IntegerValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFromFileStatement(Expression expression, String valueName) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        if(!state.getSymbolTable().isDefined(valueName) || ! (state.getSymbolTable().lookUp(valueName) instanceof IntegerValue)){
            throw new TypeMismatchException("ERROR : Could not read into " + valueName + " because it does not exist or is not of value integer.");
        }
        String fileName = StatementUtil.evaluateToString(expression, state.getSymbolTable(), state.getHeapTable());
        try {
            String readLine;
            synchronized ( state.getFileTable()) {
                BufferedReader fileReader = state.getFileTable().getFile(fileName);
                if (fileReader == null){
                    throw new FileNotOpenException("File not open");
                }
                readLine = fileReader.readLine();
            }
            int readValue = readLine == null ? 0 : Integer.parseInt(readLine);
            state.getSymbolTable().updateValue(valueName, new IntegerValue(readValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ReadFromFileStatement(expression, valueName);
    }

    @Override
    public String format() {
        return "read(" + expression.format()+ ", &" + valueName+ ")";
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        if(!expression.typecheck(typeEnvironment).equals(new StringType()))
            throw new CompilerException("Tried to use non string file name");
        if(!typeEnvironment.getTypeOfVariable(valueName).equals(new IntegerType()))
            throw new CompilerException("Tried to read from file into non integer type");
        return typeEnvironment;
    }
}
