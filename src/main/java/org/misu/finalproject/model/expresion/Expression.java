package org.misu.finalproject.model.expresion;


import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.expresion.exception.CompilerException;
import org.misu.finalproject.model.expresion.exception.ExpressionEvaluationException;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.interpretor.table.TypeTable;
import org.misu.finalproject.model.type.Type;
import org.misu.finalproject.model.value.Value;

public interface Expression extends Formatable {
    Value evaluate(SymbolTable table, HeapTable heapTable) throws ExpressionEvaluationException;
    Type typecheck(TypeTable environment) throws CompilerException;
}
