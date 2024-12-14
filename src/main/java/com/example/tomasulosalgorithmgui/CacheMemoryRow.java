package com.example.tomasulosalgorithmgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CacheMemoryRow {
    private final StringProperty address;
    private final StringProperty value;

    public CacheMemoryRow(String address, String value) {
        this.address = new SimpleStringProperty(address);
        this.value = new SimpleStringProperty(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty valueProperty() {
        return value;
    }
}

