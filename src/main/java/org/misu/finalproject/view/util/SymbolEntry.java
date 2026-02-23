package org.misu.finalproject.view.util;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymbolEntry extends ToupleTableRow<SimpleStringProperty, SimpleStringProperty> {
    public SymbolEntry(SimpleStringProperty identifier, SimpleStringProperty value) {
        super(identifier, value);
    }
}
