package org.misu.finalproject.model.interpretor.table;


import org.misu.finalproject.model.adt.CompilerMap;
import org.misu.finalproject.model.type.NullType;
import org.misu.finalproject.model.type.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TypeEnvironment implements TypeTable{
    private final CompilerMap<String, Type> typeEnvironment;

    public TypeEnvironment(CompilerMap<String, Type> typeEnvironment) {
        this.typeEnvironment = typeEnvironment;
    }

    public TypeEnvironment() {
        this.typeEnvironment = new CompilerMap<>();
    }

    @Override
    public boolean isDefined(String name) {
        return typeEnvironment.contains(name);
    }

    @Override
    public Type getTypeOfVariable(String name) {
        return typeEnvironment.getOrDefault(name, new NullType());
    }

    @Override
    public void updateTypeOfVariable(String name, Type type) {
        typeEnvironment.put(name, type);
    }

    @Override
    public TypeTable clone() {
        return new TypeEnvironment(new CompilerMap<>(typeEnvironment.getAll()));
    }
}
