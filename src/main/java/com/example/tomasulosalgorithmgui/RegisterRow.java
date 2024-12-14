package com.example.tomasulosalgorithmgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterRow {
    private final StringProperty name;
    private final StringProperty qi;
    private final StringProperty value;

    public RegisterRow(String name, String qi, String value) {
        this.name = new SimpleStringProperty(name);
        this.qi = new SimpleStringProperty(qi);
        this.value = new SimpleStringProperty(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty qiProperty() {
        return qi;
    }

    public StringProperty valueProperty() {
        return value;
    }
}
