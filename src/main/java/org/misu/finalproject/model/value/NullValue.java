package org.misu.finalproject.model.value;

import org.misu.finalproject.model.type.NullType;
import org.misu.finalproject.model.type.Type;

public class NullValue implements Value {
    @Override
    public Type getType() {
        return new NullType();
    }

    @Override
    public Value clone() {
        return new NullValue();
    }

    @Override
    public String format() {
        return "null";
    }
}
