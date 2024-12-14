package com.example.tomasulo.Memory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Memory {

    private float[] memory;
    private int size;

    public Memory(int size) {
        this.size = size;
        this.memory = new float[size];
    }

    public int getSize() {
        return size;
    }

    public float[] getMemory() {
        return memory;
    }

    public float getAddress(int index){
        return memory[index];
    }

    public void setAddress(int index, float value){
        memory[index] = value;
    }

    public void clearMemory(){
        for (int i = 0; i < size; i++) {
            memory[i] = 0;
        }
    }

    public void printMemory(){
        for (int i = 0; i < size; i++) {
            System.out.println("Address " + i + ": " + memory[i]);
        }
    }

    public void readMemoryFromFile(String fileName){

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String address = parts[0];
                String value = parts[1];
                float intValue = Float.parseFloat(value);
                memory[Integer.parseInt(address)] = intValue;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

