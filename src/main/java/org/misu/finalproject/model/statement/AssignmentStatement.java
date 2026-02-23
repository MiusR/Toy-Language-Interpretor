package org.misu.finalproject.model.statement;


import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.stack.ExecutionStack;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.interpretor.table.exception.SymbolLookupException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.Value;

public record AssignmentStatement(String symbol, Expression exp) implements Statement {

    @Override
    public String format() {
        return symbol + "=" + exp.format();
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        ExecutionStack stack = state.getStack();
        SymbolTable table = state.getSymbolTable();

        Value val = exp.evaluate(table, state.getHeapTable());
        try {
            table.updateValue(symbol, val);
        } catch (SymbolLookupException e) {
            throw new StatementExecutionException(e);
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(symbol, exp);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        Type typevar = typeEnvironment.getTypeOfVariable(symbol);
        Type typeexp = exp.typecheck(typeEnvironment);
        if(typeexp.equals(typevar))
            return typeEnvironment;
        throw  new CompilerException("Assignment type mismatch");
    }
}
