package org.misu.finalproject.model.adt;

import java.util.Vector;

public interface Stack<T> extends Iterable<T> {
    T pop();
    T peek();
    void push(T elem);
    int size();
    boolean isEmpty();
    Vector<T> getAll();
}
