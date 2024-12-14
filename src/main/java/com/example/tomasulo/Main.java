package com.example.tomasulo;

import com.example.tomasulo.InstructionUnit.InstructionMemory;


public class Main {
    public static void main(String[] args) {

        InstructionMemory instructionMemory = new InstructionMemory(20);

        instructionMemory.readInstructionsFromFile("Tomasulo/instructions.txt");

        instructionMemory.printInstructions();

        System.out.println(instructionMemory.getCurrentInstruction());
        instructionMemory.issueCurrentInstruction();
        System.out.println(instructionMemory.getCurrentInstruction());
        instructionMemory.issueCurrentInstruction();

        instructionMemory.jumpTo(4);
        System.out.println(instructionMemory.getCurrentInstruction().getSource1());


    }
}