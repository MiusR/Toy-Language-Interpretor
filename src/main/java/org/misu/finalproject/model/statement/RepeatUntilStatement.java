package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.BinaryOperatorExpression;
import org.misu.finalproject.model.expresion.ConstantExpression;
import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.Operator;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.BooleanType;
import org.misu.finalproject.model.value.BooleanValue;

public record RepeatUntilStatement(Statement loop, Expression condition) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        state.getStack().pushStatement(new WhileStatement(
                new BinaryOperatorExpression(condition,
                new ConstantExpression(new BooleanValue(false)), Operator.EQUAL)
                , loop.deepCopy()));
        state.getStack().pushStatement(loop);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new RepeatUntilStatement(loop, condition);
    }

    @Override
    public String format() {
        return "repeat { \n"+loop.format() + "} until (" + condition.format() + ");\n";
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        if (! (condition.typecheck(typeEnvironment) instanceof BooleanType)) {
            throw new CompilerException("Condition must be of a boolean value.");
        }
        loop.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
