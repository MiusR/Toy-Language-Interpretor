package org.misu.finalproject.model.adt;

import org.misu.finalproject.model.statement.Statement;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;

public class CompilerStack<T> implements Stack<T> {
    private final Vector<T> internalStack = new Vector<>();
    @Override
    public T pop() {
        return internalStack.removeLast();
    }

    @Override
    public T peek() {
        return internalStack.getLast();
    }

    @Override
    public void push(T elem) {
        internalStack.addLast(elem);
    }

    @Override
    public int size() {
        return internalStack.size();
    }

    @Override
    public boolean isEmpty() {
        return internalStack.isEmpty();
    }

    @Override
    public Vector<T> getAll() {
        return internalStack;
    }

    @Override
    public int hashCode() {
        return internalStack.hashCode();
    }

    @Override
    public Iterator<T> iterator() {
        return internalStack.stream().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        internalStack.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return internalStack.spliterator();
    }
}
