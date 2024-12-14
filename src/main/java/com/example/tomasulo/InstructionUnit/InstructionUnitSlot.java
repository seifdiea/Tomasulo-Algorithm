package com.example.tomasulo.InstructionUnit;

public class InstructionUnitSlot {

    private Instruction instruction;
    private int iteration;
    private int issueClock;
    private int startExecutionClock;
    private int finishExecutionClock;
    private int writeClockCycle;

    public InstructionUnitSlot(Instruction instruction, int iteration, int issueClock, int startExecutionClock, int finishExecutionClock, int writeClockCycle) {

        this.instruction = instruction;
        this.iteration = iteration;
        this.issueClock = issueClock;
        this.startExecutionClock = startExecutionClock;
        this.finishExecutionClock = finishExecutionClock;
        this.writeClockCycle = writeClockCycle;

    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }


    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public void setIssueClock(int issueClock) {
        this.issueClock = issueClock;
    }

    public void setStartExecutionClock(int startExecutionClock) {
        this.startExecutionClock = startExecutionClock;
    }

    public void setFinishExecutionClock(int finishExecutionClock) {
        this.finishExecutionClock = finishExecutionClock;
    }

    public void setWriteClockCycle(int writeClockCycle) {
        this.writeClockCycle = writeClockCycle;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public int getIteration() {
        return iteration;
    }

    public int getIssueClock() {
        return issueClock;
    }

    public int getStartExecutionClock() {
        return startExecutionClock;
    }

    public int getFinishExecutionClock() {
        return finishExecutionClock;
    }

    public int getWriteClockCycle() {
        return writeClockCycle;
    }

    public void setWriteResultClock(int i) {
        this.writeClockCycle = i;
    }
}
