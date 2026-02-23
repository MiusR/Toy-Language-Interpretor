package org.misu.finalproject.model.interpretor.table;


import org.misu.finalproject.model.adt.CompilerMap;
import org.misu.finalproject.model.adt.Map;
import org.misu.finalproject.model.interpretor.table.exception.SymbolLookupException;
import org.misu.finalproject.model.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


public class BasicSymbolTable implements SymbolTable{
    private final CompilerMap<String, Value> symbols;

    public BasicSymbolTable(CompilerMap<String, Value> symbols) {
        this.symbols = symbols;
    }

    public BasicSymbolTable() {
        symbols = new CompilerMap<>(); // Thread safe
    }

    @Override
    public boolean isDefined(String id) {
        return symbols.contains(id);
    }

    @Override
    public Value lookUp(String id) throws SymbolLookupException {
        if(!symbols.contains(id))
            throw new SymbolLookupException("ERROR : Variable \"" + id +"\" is not declared.");
        return symbols.get(id);
    }

    @Override
    public SymbolTable deepClone() {
        ConcurrentMap<String, Value> deepCopy = symbols.getAll().entrySet().stream()
                .collect(Collectors.toConcurrentMap(
                        java.util.Map.Entry::getKey,
                        e -> e.getValue().clone()   // Create a brand new object
                ));
        return new BasicSymbolTable(new CompilerMap<>(deepCopy));
    }

    @Override
    public void updateValue(String id, Value newValue) throws SymbolLookupException {
        if(!symbols.contains(id))
            throw new SymbolLookupException("ERROR : Variable \"" + id +"\" is not declared.");

        Value oldValue = lookUp(id);
        if (!oldValue.getType().equals(newValue.getType())) {
            throw new SymbolLookupException(
                    "ERROR: Type mismatch for variable \"" + id + "\". " +
                            "Expected " + oldValue.getType() +
                            " but got " + newValue.getType() + "."
            );
        }
        symbols.put(id, newValue);
    }

    @Override
    public void setValue(String id, Value value) {
        if(symbols.contains(id))
            throw new SymbolLookupException("ERROR : Variable \"" + id +"\" is ALREADY declared.");
        symbols.put(id, value);
    }

    @Override
    public <T extends  Value> List<T> getValuesOfType(Class<T> type) {
        return symbols.valueSet().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder("\nSymbol Table:\n");
        for(String key : symbols.keySet()) {
            builder.append(key).append(" -> ").append(symbols.get(key)).append("\n");
        }
        return builder.toString();
    }

    @Override
    public BasicSymbolTable clone() {
        return new BasicSymbolTable(new CompilerMap<>(symbols.getAll()));
    }

    @Override
    public java.util.Map<String, Value> getAll() {
        return new HashMap<>(symbols.getAll());
    }
}
