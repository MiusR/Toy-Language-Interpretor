package org.misu.finalproject.controller;

import org.misu.finalproject.model.interpretor.table.BasicSymbolTable;
import org.misu.finalproject.model.interpretor.table.HeapTable;
import org.misu.finalproject.model.interpretor.table.SymbolTable;
import org.misu.finalproject.model.type.RefType;
import org.misu.finalproject.model.value.RefValue;
import org.misu.finalproject.model.value.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GarbageCollector {

    /*
      A4:
      Goal: Clean up all unreachable values.

      1. Set<Integer> reachableAddresses = new HashSet<>();
      2. For every RefValue R in symbol table:
        3. reachableAddresses.add(R.address())
        4. If RefValue itself points to a reference,
           repeat the process for that reference and so on
         Ex: RefValue(3, RefValue(5, Integer) -> reachableAddresses.addAll(3, 5)
      5. Set<Integer> unreachableAddresses = heap.allAddresses() \ reachableAddresses
      6. Clean-up all unreachableAddresses

      A5:
      At step 2, instead of iterating a single symbol table,
      iterate through the UNION of all symbol tables.
       */
    public static HeapTable collect(SymbolTable symbolTable, HeapTable heapTable) {
        Set<Integer> reachableAddresses = new HashSet<>();
        for(RefValue refValue : symbolTable.getValuesOfType(RefValue.class)) {
            Integer address = refValue.address();
            Optional<Value> referencedVal;
            reachableAddresses.add(refValue.address());
            do {
                referencedVal = heapTable.memLook(address);
                if(referencedVal.isPresent() && referencedVal.get().getType() instanceof RefType) {
                    address = ((RefValue) referencedVal.get()).address();
                    reachableAddresses.add(address);
                }else
                    break;
            } while(referencedVal.get().getType() instanceof RefType);
        }

        Set<Integer> unreachableAddresses = new HashSet<>(heapTable.getAll().keySet())
                .stream()
                .filter(value -> !reachableAddresses.contains(value))
                .collect(Collectors.toSet());

        for(Integer addr : unreachableAddresses) {
            heapTable.memFree(addr);
        }
        return heapTable;
    }

    public static HeapTable collectAll(List<SymbolTable> symbolTableList, HeapTable heapTable) {
        SymbolTable table = new BasicSymbolTable();
        AtomicInteger code = new AtomicInteger();
        symbolTableList.forEach(t -> t.getAll().forEach( (name, val) -> {
            if (table.isDefined(name)) {
                table.setValue(name + "_mirrorC" + code.get(), val);
                code.getAndIncrement();
            }else
                table.setValue(name, val);
        })); // Merge tables
        return collect(table, heapTable);
    }
}
