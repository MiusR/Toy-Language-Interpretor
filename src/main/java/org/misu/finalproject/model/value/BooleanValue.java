package org.misu.finalproject.model.value;

import org.misu.finalproject.model.type.BooleanType;
import org.misu.finalproject.model.type.Type;

public record BooleanValue(boolean value) implements Value {

    @Override
    public Type getType() {
        return new BooleanType();
    }

    @Override
    public Value clone() {
        return new BooleanValue(value);
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
