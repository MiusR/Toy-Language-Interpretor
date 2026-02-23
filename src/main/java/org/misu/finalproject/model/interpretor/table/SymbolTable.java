package org.misu.finalproject.model.interpretor.table;

import org.misu.finalproject.model.Formatable;
import org.misu.finalproject.model.interpretor.table.exception.SymbolLookupException;
import org.misu.finalproject.model.value.Value;

import java.util.List;
import java.util.Map;

public interface SymbolTable extends Formatable {
    boolean isDefined(String id);
    Value lookUp(String id) throws SymbolLookupException;

    SymbolTable deepClone();

    /** Updates the model.model.value bound to a symbol
     * @param id - bound symbol
     * @param newValue - model.model.value to be updated to
     * @throws SymbolLookupException if the model.model.value model.value does not match or if the symbol is not bound
     */
    void updateValue(String id, Value newValue) throws SymbolLookupException;

    /** Binds a new symbol to a model.model.value
     * @param id - symbol to be bound
     * @param value - model.model.value bound to the symbol
     * @throws SymbolLookupException if symbol is already bound
     */
    void setValue(String id, Value value) throws SymbolLookupException;

    <T extends Value> List<T> getValuesOfType(Class<T> type);

    SymbolTable clone();

    Map<String, Value> getAll();
}
