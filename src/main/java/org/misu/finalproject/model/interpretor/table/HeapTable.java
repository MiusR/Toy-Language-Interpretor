package org.misu.finalproject.model.interpretor.table;


import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.interpretor.table.exception.MemoryViolationException;
import org.misu.finalproject.model.value.Value;

import java.util.Map;
import java.util.Optional;

public interface HeapTable extends Formatable {
    Integer memSet(Value value);
    Optional<Value> memLook(Integer address);
    void memFree(Integer address);
    Map<Integer, Value> getAll();
    Value memUpdate(Integer address, Value value) throws MemoryViolationException;
}
