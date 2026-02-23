package org.misu.finalproject.model.interpretor.stack;

import org.misu.finalproject.model.adt.CompilerStack;
import org.misu.finalproject.model.adt.Stack;
import org.misu.finalproject.model.interpretor.stack.exception.EmptyExecutionStack;
import org.misu.finalproject.model.statement.Statement;

import java.util.EmptyStackException;

public class BasicExecutionStack implements ExecutionStack {
    private final Stack<Statement> executionStack;

    public BasicExecutionStack(CompilerStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public BasicExecutionStack() {
        executionStack = new CompilerStack<>();
    }


    @Override
    public boolean isEmpty() {
        return executionStack.isEmpty();
    }

    @Override
    public void pushStatement(Statement s) {
        executionStack.push(s);
    }

    @Override
    public String format() {
        StringBuilder stringBuilder = new StringBuilder("Execution Stack:\n");
        for(Statement s : executionStack)
            stringBuilder.append(s.format());
        return stringBuilder.toString();
    }

    @Override
    public Statement popStatement() {
        try {
            return executionStack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyExecutionStack(e);
        }
    }

    @Override
    public java.util.Stack<Statement> getAll() {
        java.util.Stack<Statement> s = new java.util.Stack<>();
        s.addAll(executionStack.getAll());
        return s;
    }
}
