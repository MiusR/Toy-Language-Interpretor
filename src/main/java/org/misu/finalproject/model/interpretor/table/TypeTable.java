package org.misu.finalproject.model.interpretor.table;


import org.misu.finalproject.model.type.Type;

public interface TypeTable {
    boolean isDefined(String name);
    Type getTypeOfVariable(String name);
    void updateTypeOfVariable(String name, Type type);
    TypeTable clone();
}
