package com.example.tomasulo.RegisterFile;

public class IntRegister {

    private String name;
    private String value;

    public IntRegister(String name) {
        this.name = name;
        value = "0";
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }


    public String toString() {
        return name + " Value: " + value;
    }
}
