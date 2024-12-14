package com.example.tomasulo.RegisterFile;

import java.util.HashMap;

public class RegisterFile {

    private HashMap<String, Register> floatingRegisters;
    private HashMap<String, IntRegister> integerRegisters;

    public RegisterFile(int size) {
        floatingRegisters = new HashMap<>();
        for (int i = 0; i < size; i++) {
            floatingRegisters.put("F" + i, new Register("F" + i));
        }
        integerRegisters = new HashMap<>();
        for (int i = 0; i < size; i++) {
            integerRegisters.put("R" + i, new IntRegister("R" + i));
        }
    }

    public Register getFloatingRegister(String name) {
        return floatingRegisters.get(name);
    }
    public IntRegister getIntegerRegister(String name) {
        return integerRegisters.get(name);
    }

    public void setFloatingRegisterValue(String name, String value) {
        floatingRegisters.get(name).setValue(value);
    }

    public void setIntegerRegisterValue(String name, String value) {
        integerRegisters.get(name).setValue(value);
    }

    public void setRegisterQi(String name, String Qi) {
        floatingRegisters.get(name).setQi(Qi);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        // Sort and display floating-point registers in order (F0, F1, F2, ...)
        floatingRegisters.keySet().stream()
                .sorted((key1, key2) -> Integer.compare(
                        Integer.parseInt(key1.substring(1)),
                        Integer.parseInt(key2.substring(1))
                ))
                .forEach(key -> result.append(floatingRegisters.get(key).toString()).append("\n"));

        // Sort and display integer registers in order (R0, R1, R2, ...)
        integerRegisters.keySet().stream()
                .sorted((key1, key2) -> Integer.compare(
                        Integer.parseInt(key1.substring(1)),
                        Integer.parseInt(key2.substring(1))
                ))
                .forEach(key -> result.append(integerRegisters.get(key).toString()).append("\n"));

        return result.toString();
    }

    public void broadcast(String tag, String result) {
        for (Register register : floatingRegisters.values()) {
            register.broadcast(tag, result);
        }
    }

    public HashMap<String, Register> getFloatingRegisters() {
        return floatingRegisters;
    }

    public HashMap<String, IntRegister> getIntegerRegisters() {
        return integerRegisters;
    }
}
