package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.BooleanType;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.BooleanValue;
import org.misu.finalproject.model.value.Value;

public record WhileStatement(Expression expression, Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeapTable());
        if (value instanceof BooleanValue(boolean value1)) {
            if (value1) {
                state.getStack().pushStatement(this);
                state.getStack().pushStatement(statement);
            }
        } else
            throw new StatementExecutionException("Tried to evaluate non boolean value in while");
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(expression, statement);
    }

    @Override
    public String format() {
        return "while(" + expression.format() + ") {\n" + statement.format() + "}";
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        Type conditional = expression.typecheck(typeEnvironment);
        if (conditional.equals(new BooleanType())) {
            statement.typecheck(typeEnvironment.clone());
            return typeEnvironment;
        }
        throw new CompilerException("Provided non boolean parameter to while statement.");
    }
}
