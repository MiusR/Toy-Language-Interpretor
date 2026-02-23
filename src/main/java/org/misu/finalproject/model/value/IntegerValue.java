package org.misu.finalproject.model.value;

import org.misu.finalproject.model.type.IntegerType;
import org.misu.finalproject.model.type.Type;

public record IntegerValue(int value) implements Value {

    @Override
    public Type getType() {
        return new IntegerType();
    }

    @Override
    public Value clone() {
        return new IntegerValue(value);
    }

    @Override
    public String format() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return format();
    }
}
