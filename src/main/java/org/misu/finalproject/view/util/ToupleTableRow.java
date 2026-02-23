package org.misu.finalproject.view.util;

public class ToupleTableRow<T,E> {

    private final T identifier;
    private final E value;


    public ToupleTableRow(T identifier, E value) {
        this.identifier = identifier;
        this.value = value;
    }

    public T getIdentifier() {
        return identifier;
    }

    public E getValue() {
        return value;
    }
}
