package com.example.tomasulo.ReservationStations;

import com.example.tomasulo.InstructionUnit.Instruction;

public class ImmediateReservationStationSlot {
    Instruction instruction;
    int cyclesLeft;

    public ImmediateReservationStationSlot(int cyclesLeft, Instruction instruction) {
        this.cyclesLeft = cyclesLeft;
        this.instruction = instruction;
    }

    public int getCyclesLeft() {
        return cyclesLeft;
    }

    public String toString() {
        return  instruction.toString() + " " + cyclesLeft;
    }

    public void decrementCycle() {
        if(cyclesLeft > 0)
            cyclesLeft--;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Instruction getInstruction() {
        return instruction;
    }
}
