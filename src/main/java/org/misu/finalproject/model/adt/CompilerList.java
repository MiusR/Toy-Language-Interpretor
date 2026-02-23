package org.misu.finalproject.model.adt;

import org.misu.finalproject.model.adt.exception.IndexOutOfBounds;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;

public class CompilerList<T> implements List<T>{
    private final Vector<T> internalList = new Vector<T>();

    @Override
    public void add(T elem) {
        internalList.add(elem);
    }

    @Override
    public void addLast(T elem) {
        internalList.addLast(elem);
    }

    @Override
    public void addFirst(T elem) {
        internalList.addFirst(elem);
    }

    @Override
    public T removeLast() {
        return internalList.removeLast();
    }

    @Override
    public T removeFirt() {
        return internalList.removeFirst();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public T remove(int position) {
        try {
            return internalList.remove(position);
        }catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBounds("Index out of bounds: " + position);
        }
    }

    @Override
    public boolean contains(T elem) {
        return internalList.contains(elem);
    }

    @Override
    public T get(int position) {
        try {
            return internalList.get(position);
        }catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBounds("Index out of bounds: " + position);
        }
    }

    @Override
    public T getLast() {
        return internalList.getLast();
    }

    @Override
    public T getFirst() {
        return internalList.getFirst();
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public Vector<T> getAll() {
        return internalList;
    }

    @Override
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        internalList.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return internalList.spliterator();
    }
}
