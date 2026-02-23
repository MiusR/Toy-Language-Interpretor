package org.misu.finalproject.model.interpretor.table;

import org.misu.finalproject.model.adt.CompilerMap;
import org.misu.finalproject.model.statement.exception.FileDoesNotExistException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapFileTable implements FileTable{
    private final CompilerMap<String, BufferedReader> fileTable;

    public MapFileTable() {
        this.fileTable = new CompilerMap<>(); // Thread safe
    }


    @Override
    public boolean isOpen(String fileName) {
        return fileTable.contains(fileName);
    }

    @Override
    public void addFile(String name, BufferedReader reader) {
        fileTable.put(name, reader);
    }

    @Override
    public BufferedReader getFile(String fileName) {
        return fileTable.getOrDefault(fileName, null);
    }

    @Override
    public void closeFile(String fileName) {
        try {
            fileTable.delete(fileName).close();
        } catch (IOException e) {
            throw new FileDoesNotExistException("ERROR : Could not close a file that is not opened!");
        }
    }

    @Override
    public Map<String, BufferedReader> getAll() {
        return fileTable.getAll();
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder("File Table:\n");
        for(String key : fileTable.keySet()) {
            builder.append(key).append("\n");
        }
        return builder.toString();
    }
}
