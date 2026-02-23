package org.misu.finalproject.model.value;

import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.type.Type;

public interface Value extends Formatable {
    Type getType();
    Value clone();
}
