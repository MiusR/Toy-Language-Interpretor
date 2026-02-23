package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;

public record TurnaryStatement(String receiver, Expression condition, Expression thenValue, Expression elseValue) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        state.getStack().pushStatement(
                new ConditionalStatement(
                        condition,
                        new AssignmentStatement(receiver, thenValue),
                        new AssignmentStatement(receiver, elseValue)
                )
        );
        return state;
    }

    @Override
    public Statement deepCopy() {
        return new TurnaryStatement(receiver, condition, thenValue, elseValue);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        condition.typecheck(typeEnvironment);
        if(!thenValue.typecheck(typeEnvironment).equals(typeEnvironment.getTypeOfVariable(receiver)) &&
        !elseValue.typecheck(typeEnvironment).equals(typeEnvironment.getTypeOfVariable(receiver))) {
            throw new CompilerException("Error : Assignment mismatch turnary");
        }
        return typeEnvironment;
    }

    @Override
    public String format() {
        return receiver + " = " + condition.format() + " ? " + thenValue.format() + " : " + elseValue.format();
    }
}
