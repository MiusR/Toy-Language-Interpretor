package org.misu.finalproject.model.interpretor.table;


import org.misu.finalproject.model.Formatable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileTable extends Formatable {
    boolean isOpen(String fileName);
    void addFile(String name, BufferedReader reader);
    BufferedReader getFile(String fileName);
    void closeFile(String fileName) throws IOException;
    Map<String, BufferedReader> getAll();
}
