package com.example.tomasulo.InstructionUnit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InstructionMemory {

    private static int currentAddress = 0;
    private Instruction[] instructionMemory; // Array to hold instructions
    private int size;

    // Constructor to initialize instruction memory with a given size
    public InstructionMemory(int size) {
        this.size = size;
        this.instructionMemory = new Instruction[size];
    }

    // Method to add an instruction at a specific index
    public void addInstruction(int index, Instruction instruction) {
        if (index >= 0 && index < size) {
            instructionMemory[index] = instruction;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for instruction memory.");
        }
    }

    // Method to fetch an instruction by index
    public Instruction getInstruction(int index) {
        if (index >= 0 && index < size) {
            return instructionMemory[index];
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for instruction memory.");
        }
    }

    // Method to clear an instruction at a specific index
    public void clearInstruction(int index) {
        if (index >= 0 && index < size) {
            instructionMemory[index] = null;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for instruction memory.");
        }
    }

    // Method to print all instructions for debugging
    public void printInstructions() {
        for (int i = 0; i < size; i++) {
            System.out.println("Address " + i + ": " +
                    (instructionMemory[i] != null ? instructionMemory[i].toString() : "Empty"));
        }
    }

    // Method to jump to a specific instruction index (useful for branches)
    public Instruction jumpTo(int address) {
        currentAddress = address;
        return getInstruction(address); // Reuse bounds checking
    }

    // Method to get the current instruction
    public Instruction getCurrentInstruction() {
        return instructionMemory[currentAddress];
    }

    public Instruction issueCurrentInstruction() {
        return getInstruction(currentAddress++);
    }

    public void incrementCurrentAddress() {
        currentAddress++;
    }




    public void readInstructionsFromFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String operation = parts[0];
                String destination = parts[1].replace(",", "");
                String source1 = parts[2].replace(",", "");
                String source2 = parts.length > 3 ? parts[3] : null;
                Instruction instruction = new Instruction(operation, destination, source1, source2);
                addInstruction(currentAddress++, new Instruction(operation, destination, source1, source2));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(currentAddress);
        currentAddress = 0;
        System.out.println(currentAddress);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Instruction Memory: \n");
        for(int i = 0; i < size; i++) {
            if(instructionMemory[i] == null) {
                break;
            }
            sb.append(instructionMemory[i].toString());
            sb.append(" \n");
        }
        return sb.toString();


    }

}

