package org.misu.finalproject.model.interpretor.table.barrier;

import javafx.util.Pair;
import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.adt.CompilerList;
import org.misu.finalproject.model.adt.CompilerMap;

import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

public class MapBarrierTable implements BarrierTable, Formatable {
    private static Integer barrierId = 1;
    private final CompilerMap<Integer, Pair<Integer, CompilerList<Integer>>> barrierTable;

    public MapBarrierTable() {
        this.barrierTable = new CompilerMap<>();
    }

    public MapBarrierTable(ConcurrentMap<Integer, Pair<Integer, CompilerList<Integer>>> barrierTable) {
        this.barrierTable = new CompilerMap<>(barrierTable);
    }

    private synchronized int getNextInt() {
        return barrierId++;
    }

    @Override
    public synchronized int getCount(int barrierId) {
        return barrierTable.getOrDefault(barrierId, new Pair<>(-1, new CompilerList<>())).getKey();
    }

    @Override
    public synchronized int await(int barrierId, int selfId) {
        if(!barrierTable.contains(barrierId)) {
            return -1;
        }
        var pair = barrierTable.get(barrierId);
        if(!pair.getValue().contains(selfId)) {
            pair.getValue().add(selfId);
        }
        return pair.getValue().size();
    }

    @Override
    public synchronized int createBarrier(int size) {
        int id =getNextInt();
        barrierTable.put(id, new Pair<>(size, new CompilerList<>()));
        return id;
    }

    @Override
    public ConcurrentMap<Integer, Pair<Integer, CompilerList<Integer>>> getAll() {
        return barrierTable.getAll();
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder("Barrier Table:\n");
        for(Integer key : barrierTable.keySet()) {
            builder.append(key).append(" -> (").append(barrierTable.get(key).getKey().toString()).append(", [").append(
                    barrierTable.get(key).getValue().getAll().stream().map(Object::toString).reduce(
                            (x, y) -> x.concat(", ").concat(y)
                    ).orElse("")
            ).append("])\n");
        }
        return builder.toString();
    }
}
