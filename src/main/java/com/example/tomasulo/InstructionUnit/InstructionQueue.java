package com.example.tomasulo.InstructionUnit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InstructionQueue {

    private Instruction[] instructions;
    private int head;
    private int tail;
    private int size;
    private int maxSize;
    private BufferedReader br;

    public InstructionQueue(int maxSize) {
        this.maxSize = maxSize;
        instructions = new Instruction[maxSize];
        head = 0;
        tail = 0;
        size = 0;
    }

    public void enqueue(Instruction instruction) {
        if(size == maxSize) {
            throw new IllegalStateException("Queue is full");
        }
        instructions[tail] = instruction;
        tail = (tail + 1) % maxSize;
        size++;
    }

    public Instruction dequeue() {
        if(size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        Instruction instruction = instructions[head];
        head = (head + 1) % maxSize;
        size--;
        readInstructionsFromFile("tomasulo/instructions.txt");
        return instruction;
    }

    public Instruction peek() {
        if(size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return instructions[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == maxSize;
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Instruction Queue: ");
        for(int i = 0; i < size; i++) {
            sb.append(instructions[(head + i) % maxSize].toString());
            sb.append(" \n");
        }
        return sb.toString();
    }

    public void readInstructionsFromFile(String filePath) {
        try {
            if (br == null) {
                br = new BufferedReader(new FileReader(filePath));
            }
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(" ");
                String operation = parts[0];
                String destination = parts[1].replace(",", "");
                String source1 = parts[2].replace(",", "");
                String source2 = parts.length > 3 ? parts[3] : null;
                Instruction instruction = new Instruction(operation, destination, source1, source2);
                enqueue(instruction);
                if(isFull()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InstructionQueue queue = new InstructionQueue(5);
        queue.readInstructionsFromFile("Tomasulo/instructions.txt");
        System.out.println(queue);

        Instruction instruction = queue.dequeue();
        System.out.println("Dequeued instruction: " + instruction);

        //queue.readInstructionsFromFile("instructions.txt");
        System.out.println("Queue after dequeue:");
        System.out.println(queue);

        Instruction instruction2 = queue.dequeue();

        System.out.println("Dequeued instruction: " + instruction2);
        System.out.println("Queue after dequeue:");
        System.out.println(queue);

        Instruction instruction3 = queue.dequeue();
        System.out.println("Dequeued instruction: " + instruction3);
        System.out.println("Queue after dequeue:");
        System.out.println(queue);

        Instruction instruction4 = queue.dequeue();
        System.out.println("Dequeued instruction: " + instruction4);
        System.out.println("Queue after dequeue:");
        System.out.println(queue);


    }

}
