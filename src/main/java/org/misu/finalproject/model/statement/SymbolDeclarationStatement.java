package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.Type;

public record SymbolDeclarationStatement(String symbol, Type type) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        SymbolTable table = state.getSymbolTable();
        table.setValue(symbol, type.defaultValue());
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new SymbolDeclarationStatement(symbol, type);
    }

    @Override
    public String format() {
        return type.format() + " " + symbol;
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        typeEnvironment.updateTypeOfVariable(symbol, type);
        return typeEnvironment;
    }
}
