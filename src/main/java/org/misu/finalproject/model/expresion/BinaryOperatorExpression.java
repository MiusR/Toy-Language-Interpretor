package org.misu.finalproject.model.expresion;

import org.misu.finalproject.model.value.IntegerValue;
import org.misu.finalproject.model.value.BooleanValue;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.type.BooleanType;
import org.misu.finalproject.model.type.IntegerType;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.StringValue;
import org.misu.finalproject.model.value.Value;

import java.util.Objects;

public record BinaryOperatorExpression(Expression firstValue, Expression secondValue,
                                       Operator operator) implements Expression {

    @Override
    public String toString() {
        return "("+firstValue.format() + " " +operator.getValue()  + " " + secondValue + ")";
    }

    @Override
    public Value evaluate(SymbolTable table, HeapTable heapTable) throws ExpressionEvaluationException {
        Value leftTerm = firstValue.evaluate(table, heapTable);
        Value rightTerm = secondValue.evaluate(table, heapTable);

        final boolean sameTypes = leftTerm.getType().equals(rightTerm.getType());
        if (!sameTypes) {
            throw new ExpressionEvaluationException("ERROR : Wrong model.value of operands provided!");
        }
        switch (operator) {
            case INVALID ->
                    throw new ExpressionEvaluationException("ERROR : Provided unknown operator between operands.");
            case ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION -> {
                if ((leftTerm instanceof IntegerValue leftValue) && (rightTerm instanceof IntegerValue rightValue))
                    return arithmeticEvaluation(leftValue, rightValue, operator);
            }

            case AND, OR -> {
                if (leftTerm instanceof BooleanValue leftValue && rightTerm instanceof BooleanValue rightValue)
                    return logicalEvaluation(leftValue, rightValue, operator);
            }

            case GREATER, GREATER_EQ, LESSER, LESSER_EQ -> {
                if ((leftTerm instanceof IntegerValue leftValue) && (rightTerm instanceof IntegerValue rightValue))
                    return relationalEvaluation(leftValue, rightValue, operator);
            }

            case EQUAL, NOT_EQUAL -> {
                if (leftTerm instanceof BooleanValue leftValue && rightTerm instanceof BooleanValue rightValue)
                    return logicalEvaluation(leftValue, rightValue, operator);
                if ((leftTerm instanceof IntegerValue leftValue) && (rightTerm instanceof IntegerValue rightValue))
                    return relationalEvaluation(leftValue, rightValue, operator);
                if ((leftTerm instanceof StringValue leftValue) && (rightTerm instanceof StringValue rightValue))
                    return stringComparison(leftValue, rightValue, operator);
            }
        }

        throw new ExpressionEvaluationException("ERROR : Could not evaluate non expression!");
    }

    @Override
    public Type typecheck(TypeTable environment) throws CompilerException {
        Type firstExpressionType, secondExpressionType;
        firstExpressionType = firstValue.typecheck(environment);
        secondExpressionType = secondValue.typecheck(environment);

        if (firstExpressionType.equals(secondExpressionType))
            return switch (operator) {
                case ADDITION, SUBTRACTION,  MULTIPLICATION, DIVISION -> new IntegerType();
                case GREATER, GREATER_EQ, LESSER, LESSER_EQ, EQUAL, NOT_EQUAL, AND, OR -> new BooleanType();
                default -> throw new CompilerException("Operator is not recognized");
            };

        throw new CompilerException("Parameter type mismatch, attempted to do " + operator.toString() + " operation with: " + firstExpressionType.format() + " and " + secondExpressionType.format());
    }

    private BooleanValue logicalEvaluation(BooleanValue leftValue, BooleanValue rightValue, Operator operator) {
        return switch (operator) {
            case AND -> new BooleanValue(leftValue.value() && rightValue.value());
            case OR -> new BooleanValue(leftValue.value() || rightValue.value());
            case EQUAL -> new BooleanValue(leftValue.value() == rightValue.value());
            case NOT_EQUAL -> new BooleanValue(leftValue.value() != rightValue.value());
            default -> throw new ExpressionEvaluationException("ERROR : Wrong model.value of operator provided!");
        };
    }

    private IntegerValue arithmeticEvaluation(IntegerValue leftValue, IntegerValue rightValue, Operator operator) {
        return switch (operator) {
            case ADDITION -> new IntegerValue(leftValue.value() + rightValue.value());
            case SUBTRACTION -> new IntegerValue(leftValue.value() - rightValue.value());
            case DIVISION -> {
                if (rightValue.value() == 0)
                    throw new ExpressionEvaluationException("ERROR : Division by ZERO!");
                yield new IntegerValue(leftValue.value() / rightValue.value());
            }
            case MULTIPLICATION -> new IntegerValue(leftValue.value() * rightValue.value());
            default -> throw new ExpressionEvaluationException("ERROR : Wrong model.value of operator provided!");
        };
    }

    private BooleanValue relationalEvaluation(IntegerValue leftValue, IntegerValue rightValue, Operator operator) {
        return switch (operator) {
            case GREATER -> new BooleanValue(leftValue.value() > rightValue.value());
            case GREATER_EQ -> new BooleanValue(leftValue.value() >= rightValue.value());
            case LESSER -> new BooleanValue(leftValue.value() < rightValue.value());
            case LESSER_EQ -> new BooleanValue(leftValue.value() <= rightValue.value());
            case EQUAL -> new BooleanValue(leftValue.value() == rightValue.value());
            case NOT_EQUAL -> new BooleanValue(leftValue.value() != rightValue.value());
            default -> throw new ExpressionEvaluationException("ERROR : Wrong model.value of operator provided!");
        };
    }

    private BooleanValue stringComparison(StringValue leftValue, StringValue rightValue, Operator operator) {
        return switch (operator) {
            case EQUAL -> new BooleanValue(leftValue.value().equals(rightValue.value()));
            case NOT_EQUAL -> new BooleanValue(!(leftValue.value().equals(rightValue.value())));
            default -> throw new ExpressionEvaluationException("ERROR : Wrong model.value of operator provided!");
        };
    }
}
