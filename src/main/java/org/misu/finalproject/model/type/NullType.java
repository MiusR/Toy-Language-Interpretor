package org.misu.finalproject.model.type;

import org.misu.finalproject.model.value.NullValue;
import org.misu.finalproject.model.value.Value;

public class NullType implements Type{
    @Override
    public Value defaultValue() {
        return new NullValue();
    }
}
