package org.misu.finalproject.model.interpretor.stack;


import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.statement.Statement;

import java.util.Stack;

public interface ExecutionStack extends Formatable {
    boolean isEmpty();
    void pushStatement(Statement s);
    Statement popStatement();
    Stack<Statement> getAll();
}
