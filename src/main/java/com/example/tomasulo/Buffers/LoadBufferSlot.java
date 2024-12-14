package com.example.tomasulo.Buffers;

import com.example.tomasulo.InstructionUnit.Instruction;

public class LoadBufferSlot {
    boolean isBusy;
    int cyclesLeft;
    String tag;
    String address;
    private static final String tagPrefix = "L";
    int issuedClockCycle;
    boolean currentlyExecuting = false;
    Instruction instruction;

    public LoadBufferSlot(int counter) {
        address = "";
        tag = tagPrefix + counter;
        cyclesLeft = 0;
        isBusy = false;
        issuedClockCycle = 0;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Instruction getInstruction() {
        return instruction;
    }


    public String getTag() {
        return tag;
    }

    public int getCycles() {
        return cyclesLeft;
    }

    public void setCycle(int cycles) {
        cyclesLeft = cycles;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean isFree() {
        return !isBusy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
         this.address = address;
    }

    public String toString() {
        return tag + " " + isBusy + " " + address + " " + cyclesLeft;
    }

    public void decrementCycle() {
        if(cyclesLeft >= 0 && currentlyExecuting)
            cyclesLeft--;
        if(cyclesLeft ==-1)
            clear();
    }

    public void clear() {
        isBusy = false;
        address = "";
        currentlyExecuting = false;
    }
    public void setExecuting(boolean executing) {
        currentlyExecuting = executing;
    }

    public boolean isExecuting() {
        return currentlyExecuting;
    }

    public int getIssueClockCycle() {
        return issuedClockCycle;
    }

    public void setIssueClockCycle(int clockCycle) {
        issuedClockCycle = clockCycle;
    }


}
