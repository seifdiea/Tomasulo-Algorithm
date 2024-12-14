package com.example.tomasulosalgorithmgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoadBufferRow {
    private final StringProperty tag;
    private final StringProperty busy;
    private final StringProperty address;
    private final StringProperty cycles;

    public LoadBufferRow(String tag, String busy, String address, String cycles) {
        this.tag = new SimpleStringProperty(tag);
        this.busy = new SimpleStringProperty(busy);
        this.address = new SimpleStringProperty(address);
        this.cycles = new SimpleStringProperty(cycles);
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public StringProperty busyProperty() {
        return busy;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty cyclesProperty() {
        return cycles;
    }
}

