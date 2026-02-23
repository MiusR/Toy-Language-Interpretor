package org.misu.finalproject.model.interpretor.streams;


import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.adt.CompilerList;
import org.misu.finalproject.model.adt.List;

import java.util.LinkedList;


public class BasicOut implements Out {
    private final List<Object> output;

    public BasicOut() {
        this.output = new CompilerList<>(); // Vector is thread safe but slower :)
    }

    @Override
    public void add(Object value) {
        output.addLast(value);
        if(value instanceof Formatable formatable)
            System.out.println(formatable.format());
        else
            System.out.println(value);
    }

    @Override
    public String toString() {
        return output.toString();
    }

    @Override
    public String getLast() {return  output.getLast().toString();}

    @Override
    public void clear() {
        output.clear();
    }

    @Override
    public java.util.List<Object> getAll() {
        return output.getAll();
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder("Out:\n");
        for(Object out : output) {
            if (out instanceof Formatable formatableObject) {
                builder.append(formatableObject.format()).append("\n");
                continue;
            }
            builder.append(out.toString()).append("\n");
        }
        return builder.toString();
    }
}
