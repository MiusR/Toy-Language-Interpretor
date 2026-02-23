package org.misu.finalproject.model.expresion;

public enum Operator {
    ADDITION("+"),
    SUBTRACTION("-"),
    DIVISION("/"),
    MULTIPLICATION("*"),
    AND("&&"),
    OR("||"),
    INVALID(""),
    GREATER(">"),
    LESSER("<"),
    GREATER_EQ(">="),
    LESSER_EQ("<="),
    EQUAL("=="),
    NOT_EQUAL("!="),

    ;
    String value;
    Operator(String operator) {
        this.value = operator;
    }

    public String getValue() {
        return value;
    }

    public Operator getOperatorType(String operator) {
        switch (operator) {
            case "+":
                return ADDITION;
            case "-":
                return SUBTRACTION;
            case "*":
                return MULTIPLICATION;
            case "/":
                return DIVISION;
            case "&&":
                return AND;
            case "||":
                return OR;
            case ">":
                return GREATER;
            case ">=":
                return GREATER_EQ;
            case "<":
                return LESSER;
            case "<=":
                return LESSER_EQ;
            case "==":
                return EQUAL;
            case "!=":
                return NOT_EQUAL;
        }
        return INVALID;
    }

}
