package org.misu.finalproject.model.type;

import org.misu.finalproject.model.value.StringValue;
import org.misu.finalproject.model.value.Value;

public record StringType() implements Type{
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
