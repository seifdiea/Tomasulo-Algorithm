package com.example.tomasulo.ReservationStations;

import com.example.tomasulo.ExecutionQueue.ExecutionResult;
import com.example.tomasulo.InstructionUnit.Instruction;

public abstract class ReservationStationSlot {

    Instruction instruction;
    String tag;
    boolean isBusy;
    int cyclesLeft;
    String operation;
    String Vj;
    String Vk;
    String Qj;
    String Qk;
    int issueClockCycle;


    public ReservationStationSlot(String tagPrefix, int counter) {
        instruction = null;
        tag = tagPrefix + counter;
        isBusy = false;
        cyclesLeft = 0;
        operation = "";
        Vj = "0";
        Vk = "0";
        Qj = "0";
        Qk = "0";
        issueClockCycle = 0;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void setOperation(String op) {
        operation = op;
    }

    public void setVj(String vj) {
        Vj = vj;
    }

    public void setVk(String vk) {
        Vk = vk;
    }

    public void setQj(String qj) {
        Qj = qj;
    }

    public void setQk(String qk) {
        Qk = qk;
    }

    public void setCycle(int cycles) {
        cyclesLeft = cycles;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public int getCyclesLeft() {
        return cyclesLeft;
    }

    public String getOperation() {
        return operation;
    }

    public String getVj() {
        return Vj;
    }

    public String getVk() {
        return Vk;
    }

    public String getQj() {
        return Qj;
    }

    public String getQk() {
        return Qk;
    }

    public String getTag() {
        return tag;
    }

    public void decrementCycle() {
        if(cyclesLeft >= 0 && isReady())
            cyclesLeft--;
        if(cyclesLeft == -1)
            clear();
    }

    public int getIssueClockCycle() {
        return issueClockCycle;
    }

    public void setIssueClockCycle(int clockCycle) {
        issueClockCycle = clockCycle;
    }



    public void clear() {
        instruction = null;
        isBusy = false;
        cyclesLeft = 0;
        operation = "";
        Vj = "0";
        Vk = "0";
        Qj = "0";
        Qk = "0";
    }



    public boolean isDone() {
        return isBusy && cyclesLeft == 0;
    }

    public boolean isReady(){
        return Qj.equals("0") && Qk.equals("0");
    }


    public void broadcast(String tag, String result) {
        if(Qj.equals(tag)) {
            Vj = result;
            Qj = "0";
        }
        if(Qk.equals(tag)) {
            Vk = result;
            Qk = "0";
        }
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public String toString() {
        return tag + " " + operation + " " + Vj + " " + Vk + " " + Qj + " " + Qk + " " + cyclesLeft + " " + issueClockCycle;
    }

    public boolean isFree() {
        return !isBusy;
    }


    public ExecutionResult execute() {
        String result = "";
        if(getCyclesLeft()>0) return null;

        switch(operation) {
            case "ADD.D":
            case "ADD.S":
                result += String.valueOf(Float.parseFloat(getVj()) + Float.parseFloat(getVk())); break;
            case "SUB.D":
            case "SUB.S":
                result += String.valueOf(Float.parseFloat(getVj()) - Float.parseFloat(getVk())); break;
            case "MUL.D":
            case "MUL.S":
                result += String.valueOf(Float.parseFloat(getVj()) * Float.parseFloat(getVk())); break;
            case "DIV.D":
            case "DIV.S":
                if (Float.parseFloat(getVk()) != 0.0) { // Avoid division by zero
                    result = String.valueOf(Float.parseFloat(getVj()) / Float.parseFloat(getVk()));
                } else {
                    result = "Division by zero!";
                } break;
            default: return null;
        }

        return new ExecutionResult(result, issueClockCycle, tag);


    }


}