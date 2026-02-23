package org.misu.finalproject.model.interpretor.table.barrier;

import javafx.util.Pair;
import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.adt.CompilerList;

import java.util.Vector;
import java.util.concurrent.ConcurrentMap;

public interface BarrierTable extends Formatable {
    int getCount(int barrierId);
    int await(int barrierId, int selfId);
    int createBarrier(int size);
    ConcurrentMap<Integer, Pair<Integer, CompilerList<Integer>>> getAll();
}
