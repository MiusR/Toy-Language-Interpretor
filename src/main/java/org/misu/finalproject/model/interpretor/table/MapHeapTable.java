package org.misu.finalproject.model.interpretor.table;


import org.misu.finalproject.model.adt.CompilerMap;
import org.misu.finalproject.model.interpretor.table.exception.MemoryViolationException;
import org.misu.finalproject.model.value.Value;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MapHeapTable implements HeapTable{
    private final CompilerMap<Integer, Value> heapTable;
    private static Integer freeAddr = 1;
    public MapHeapTable() {
        this.heapTable = new CompilerMap<>();
    }


    @Override
    public Integer memSet(Value value) {
        Integer oldAddr = freeAddr;
        freeAddr++;
        heapTable.put(oldAddr, value);
        return oldAddr;
    }

    @Override
    public Optional<Value> memLook(Integer address) {
        if(!heapTable.contains(address))
            return Optional.empty();
        return Optional.of(heapTable.get(address));
    }

    @Override
    public void memFree(Integer address) {
        heapTable.delete(address);
    }

    @Override
    public Map<Integer, Value> getAll() {
        return heapTable.getAll();
    }

    @Override
    public Value memUpdate(Integer address, Value value) throws MemoryViolationException {
        if (address == 0){
            throw new MemoryViolationException("Accessed null");
        }
        if(!heapTable.contains(address)){
            throw new MemoryViolationException("Out of bounds");
        }
        return heapTable.put(address,value);
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder("Heap Table:\n");
        for(Integer key : heapTable.keySet()) {
            builder.append(key).append(" -> ").append(heapTable.get(key).format()).append("\n");
        }
        return builder.toString();
    }
}
