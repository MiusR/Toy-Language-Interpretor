package org.misu.finalproject.model.adt;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface Map<T, U> {
    U put(T key, U element);
    U get(T key);
    U delete(T key);
    boolean contains(T key);
    int size();
    Set<T> keySet();
    Collection<U> valueSet();
    ConcurrentMap<T, U> getAll();
    U getOrDefault(T key, U def);
}
