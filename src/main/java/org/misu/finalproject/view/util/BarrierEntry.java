package org.misu.finalproject.view.util;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

public record BarrierEntry(SimpleIntegerProperty identifier, SimpleIntegerProperty value, SimpleStringProperty waitIds) {
}
