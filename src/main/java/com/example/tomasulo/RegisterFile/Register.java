package com.example.tomasulo.RegisterFile;

public class Register {

    private String name;
    private String Qi;
    private String value;

    public Register(String name) {
        this.name = name;
        Qi = "0";
        value = "0";
    }

    public void setQi(String Qi) {
        this.Qi = Qi;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getQi() {
        return Qi;
    }

    public String getValue() {
        return value;
    }

    public void resetQi() {
        Qi = "0";
    }

    public String toString() {
        return name + " Qi: " + Qi + " Value: " + value;
    }

    public void broadcast(String tag, String result) {
        if (Qi.equals(tag)) {
            value = result;
            Qi = "0";
        }
    }
}
