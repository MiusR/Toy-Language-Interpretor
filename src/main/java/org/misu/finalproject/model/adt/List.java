package org.misu.finalproject.model.adt;

import java.util.Vector;

public interface List<T> extends Iterable<T> {
    void add(T elem);
    void addLast(T elem);
    void addFirst(T elem);
    T removeLast();
    T removeFirt();
    T remove(int position);
    boolean contains(T elem);
    T get(int position);
    T getLast();
    T getFirst();
    int size();
    Vector<T> getAll();
    void clear();
}
