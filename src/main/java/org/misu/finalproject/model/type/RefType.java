package org.misu.finalproject.model.type;

import org.misu.finalproject.model.value.RefValue;
import org.misu.finalproject.model.value.Value;

public record RefType(Type inner) implements Type {

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }
}
