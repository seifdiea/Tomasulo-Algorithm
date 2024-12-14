package com.example.tomasulosalgorithmgui;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReservationStationRow {
    private final StringProperty cycles;
    private final StringProperty tag;
    private final StringProperty busy;
    private final StringProperty operation;
    private final StringProperty vj;
    private final StringProperty vk;
    private final StringProperty qj;
    private final StringProperty qk;
    private final StringProperty address;

    public ReservationStationRow(String cycles, String tag, String busy, String operation, String vj, String vk, String qj, String qk, String address) {
        this.cycles = new SimpleStringProperty(cycles);
        this.tag = new SimpleStringProperty(tag);
        this.busy = new SimpleStringProperty(busy);
        this.operation = new SimpleStringProperty(operation);
        this.vj = new SimpleStringProperty(vj);
        this.vk = new SimpleStringProperty(vk);
        this.qj = new SimpleStringProperty(qj);
        this.qk = new SimpleStringProperty(qk);
        this.address = new SimpleStringProperty(address);
    }

    public StringProperty cyclesProperty() {
        return cycles;
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public StringProperty busyProperty() {
        return busy;
    }

    public StringProperty operationProperty() {
        return operation;
    }

    public StringProperty vjProperty() {
        return vj;
    }

    public StringProperty vkProperty() {
        return vk;
    }

    public StringProperty qjProperty() {
        return qj;
    }

    public StringProperty qkProperty() {
        return qk;
    }

    public StringProperty addressProperty() {
        return address;
    }
}

