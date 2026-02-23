package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.BooleanType;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.BooleanValue;
import org.misu.finalproject.model.value.Value;

public record ConditionalStatement(Expression condition, Statement thenStatement,
                                   Statement elseStatement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException {
        Value evaluation = condition.evaluate(state.getSymbolTable(), state.getHeapTable());
        if (evaluation instanceof BooleanValue(boolean value)) {
            if (value) {
                state.getStack().pushStatement(thenStatement);
                return null;
            } else {
                state.getStack().pushStatement(elseStatement);
                return null;
            }
        }
        throw new StatementExecutionException("ERROR : Condition must be a logical truth model.model.value.");
    }

    @Override
    public Statement deepCopy() {
        return new ConditionalStatement(condition,thenStatement,elseStatement);
    }

    @Override
    public String format() {
        StringBuilder stringBuilder = new StringBuilder("If(");
        stringBuilder.append(condition.format()).append(") {\n");
        stringBuilder.append(thenStatement.format()).append("} else {\n");
        stringBuilder.append(elseStatement.format()).append("}");
        return stringBuilder.toString();
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        Type conditional = condition.typecheck(typeEnvironment);
        if(conditional.equals(new BooleanType())) {
            thenStatement.typecheck(typeEnvironment.clone());
            elseStatement.typecheck(typeEnvironment.clone());
            return typeEnvironment;
        }
        throw new CompilerException("Provided non boolean value for conditional statement");
    }
}
