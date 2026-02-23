package org.misu.finalproject.model.type;

import org.misu.finalproject.model.value.IntegerValue;
import org.misu.finalproject.model.value.Value;

public record IntegerType() implements Type{
    @Override
    public Value defaultValue() {
        return new IntegerValue(0);
    }
}
