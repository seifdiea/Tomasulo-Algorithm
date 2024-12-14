package com.example.tomasulosalgorithmgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StoreBufferRow {
    private final StringProperty tag;
    private final StringProperty busy;
    private final StringProperty address;
    private final StringProperty v;
    private final StringProperty q;
    private final StringProperty cycles;

    public StoreBufferRow(String tag, String busy, String address, String v, String q, String cycles) {
        this.tag = new SimpleStringProperty(tag);
        this.busy = new SimpleStringProperty(busy);
        this.address = new SimpleStringProperty(address);
        this.v = new SimpleStringProperty(v);
        this.q = new SimpleStringProperty(q);
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

    public StringProperty vProperty() {
        return v;
    }

    public StringProperty qProperty() {
        return q;
    }

    public StringProperty cyclesProperty() {
        return cycles;
    }
}

