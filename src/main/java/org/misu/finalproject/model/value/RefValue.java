package org.misu.finalproject.model.value;

import org.misu.finalproject.model.type.RefType;
import org.misu.finalproject.model.type.Type;

public record RefValue(int address, Type locationType) implements Value {

    @Override
    public String format() {
        return "Ref ("+locationType.toString()+") at 0x"+address;
    }

    @Override
    public String toString() {
        return "0x" + address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value clone() {
        return new RefValue(address, locationType);
    }
}
