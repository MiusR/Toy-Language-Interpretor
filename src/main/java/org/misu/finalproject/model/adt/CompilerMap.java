package org.misu.finalproject.model.adt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public class CompilerMap<K, V> implements Map<K, V>{
    private final ConcurrentMap<K, V> internalMap;

    public CompilerMap(ConcurrentMap<K, V> symbols) {
        this.internalMap = symbols;
    }


    public CompilerMap() {
        internalMap = new ConcurrentHashMap<>();
    }

    @Override
    public V put(K key, V element) {
        return internalMap.put(key, element);
    }

    @Override
    public V get(K key) {
        return internalMap.get(key);
    }

    @Override
    public V delete(K key) {
        return internalMap.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return internalMap.containsKey(key);
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public Set<K> keySet() {
        return internalMap.keySet();
    }

    @Override
    public Collection<V> valueSet() {
        return internalMap.values();
    }

    @Override
    public ConcurrentMap<K, V> getAll() {
        return internalMap;
    }

    @Override
    public V getOrDefault(K key, V def) {
        return internalMap.getOrDefault(key,def);
    }
}
