package org.misu.finalproject.model.statement;

import org.misu.finalproject.model.expresion.BinaryOperatorExpression;
import org.misu.finalproject.model.expresion.Expression;
import org.misu.finalproject.model.expresion.Operator;
import org.misu.finalproject.model.expresion.VariableExpression;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;
import org.misu.finalproject.model.type.IntegerType;

public record ForStatement(Expression initialValue, Expression maxValue, Expression step, Statement loop) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException {
        state.getStack().pushStatement(
                new CompoundStatement(
                        new SymbolDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(
                            new AssignmentStatement("v", initialValue),
                            new WhileStatement(new BinaryOperatorExpression(new VariableExpression("v"), maxValue, Operator.LESSER),
                                    new CompoundStatement(
                                            loop,
                                            new AssignmentStatement("v", step)
                                    )))
                        )
                );
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ForStatement(initialValue, maxValue, step, loop);
    }

    @Override
    public TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        typeEnvironment.updateTypeOfVariable("v", new IntegerType());
        loop.typecheck(typeEnvironment);
        if(!(initialValue.typecheck(typeEnvironment) instanceof IntegerType) || !(maxValue.typecheck(typeEnvironment) instanceof IntegerType) || !(step.typecheck(typeEnvironment) instanceof IntegerType)) {
            throw new CompilerException("Error : For can only take integer values as loop params");
        }
        return typeEnvironment;
    }

    @Override
    public String format() {
        return "for(v=" + initialValue.format() + "; v < " + maxValue.format() + "; v = " + step.format() + ") {\n" + loop.format() + "\n}";
    }
}
