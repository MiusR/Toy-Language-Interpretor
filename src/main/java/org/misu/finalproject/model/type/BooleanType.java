package org.misu.finalproject.model.type;

import org.misu.finalproject.model.value.BooleanValue;
import org.misu.finalproject.model.value.Value;

public record BooleanType() implements Type{
    @Override
    public Value defaultValue() {
        return new BooleanValue(false);
    }
}
