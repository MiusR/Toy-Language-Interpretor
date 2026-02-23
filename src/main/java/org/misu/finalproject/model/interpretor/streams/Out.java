package org.misu.finalproject.model.interpretor.streams;

import org.misu.finalproject.model.Formatable;

import java.util.List;

public interface Out extends Formatable {

    void add(Object value);

    String getLast();

    void clear();

    List<Object> getAll();
}
