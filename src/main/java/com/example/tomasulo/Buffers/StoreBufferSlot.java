package com.example.tomasulo.Buffers;

import com.example.tomasulo.InstructionUnit.Instruction;

public class StoreBufferSlot {
    boolean isBusy;
    int cyclesLeft;
    String tag;
    String address;
    String V;
    String Q;
    private static final String tagPrefix = "S";
    int issuedClockCycle;
    boolean currentlyExecuting = false;
    Instruction instruction;

    public StoreBufferSlot(int counter) {
        address = "";
        tag = tagPrefix + counter;
        cyclesLeft = 0;
        isBusy = false;
        V = "0";
        Q = "0";
        issuedClockCycle = 0;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public String getV() {
        return V;
    }

    public String getQ() {
        return Q;
    }

    public void setV(String v) {
        V = v;
    }

    public void setQ(String q) {
        Q = q;
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
        return tag + " " + isBusy + " " + address + " " + V + " " + Q;
    }

    public void broadcast(String tag, String result) {
        if(Q.equals(tag)) {
            V = result;
            Q = "0";
        }
    }

    public boolean isReady(){
        return Q.equals("0");
    }

    public void decrementCycle() {
        if(cyclesLeft >= 0 && isReady() && currentlyExecuting)
            cyclesLeft--;
        if(cyclesLeft == -1)
            clear();
    }

    public void setExecuting(boolean executing) {
        currentlyExecuting = executing;
    }

    public boolean isExecuting() {
        return currentlyExecuting;
    }

    public void clear() {
        isBusy = false;
        address = "";
        currentlyExecuting = false;
    }


    public int getIssueClockCycle() {
        return issuedClockCycle;
    }

    public void setIssueClockCycle(int clockCycle) {
        issuedClockCycle = clockCycle;
    }




}