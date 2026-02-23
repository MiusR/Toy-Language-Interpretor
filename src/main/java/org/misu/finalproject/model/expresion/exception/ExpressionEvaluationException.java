package org.misu.finalproject.model.expresion.exception;

public class ExpressionEvaluationException extends RuntimeException {
    public ExpressionEvaluationException(String s){
        super(s);
    }

    public ExpressionEvaluationException(RuntimeException e) {
        super(e);
    }
}
