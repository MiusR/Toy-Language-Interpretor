package org.misu.finalproject.model.value;

import org.misu.finalproject.model.type.StringType;
import org.misu.finalproject.model.type.Type;

public record StringValue(String value) implements Value {

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value clone() {
        return new StringValue(value);
    }

    @Override
    public String format() {
        return "\"" + value + "\"";
    }

    @Override
    public String toString() {
        return format();
    }
}
