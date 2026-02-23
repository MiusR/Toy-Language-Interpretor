package org.misu.finalproject.model.statement;


import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.ProgramState;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.statement.exception.InvalidTypeException;
import org.misu.finalproject.model.statement.exception.StatementExecutionException;

public interface Statement extends Formatable {
    ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, InvalidTypeException;
    Statement deepCopy();
    default TypeTable typecheck(TypeTable typeEnvironment) throws CompilerException {
        return typeEnvironment;
    }
}
