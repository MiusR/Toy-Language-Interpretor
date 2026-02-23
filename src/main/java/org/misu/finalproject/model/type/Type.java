package org.misu.finalproject.model.type;

import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.value.Value;

public interface Type extends Formatable {
    Value defaultValue();

    @Override
    default String format() {
        return defaultValue().getClass().getSimpleName();
    }
}
