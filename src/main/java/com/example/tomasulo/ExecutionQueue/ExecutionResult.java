package com.example.tomasulo.ExecutionQueue;

public class ExecutionResult implements Comparable {
    public String result;
    public int issuedClockCycles;
    public String tag;

    public ExecutionResult(String result, int issuedClockCycles, String tag) {
        this.result = result;
        this.issuedClockCycles = issuedClockCycles;
        this.tag = tag;
    }
    public String toString() {
        return result + " " + issuedClockCycles + " " + tag;
    }


    @Override
    public int compareTo(Object o) {
        ExecutionResult other = (ExecutionResult) o;
        return this.issuedClockCycles - other.issuedClockCycles;
    }

    public String getTag() {
        return tag;
    }

    public String getResult() {
        return result;
    }

    public int getIssuedClockCycles() {
        return issuedClockCycles;
    }
}
