package org.misu.finalproject.model;

public interface Formatable {
    default String format() {
        return this.toString();
    }
}
