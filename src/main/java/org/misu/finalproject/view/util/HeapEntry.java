package org.misu.finalproject.view.util;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeapEntry extends ToupleTableRow<SimpleIntegerProperty, SimpleStringProperty> {
    public HeapEntry(SimpleIntegerProperty identifier, SimpleStringProperty value) {
        super(identifier, value);
    }
}
