package com.example.tomasulo.Buffers;

import java.util.ArrayList;

public class LoadBuffer {
    private ArrayList<LoadBufferSlot> slots;

    public LoadBuffer(int size) { // in our case it should be 3
        slots = new ArrayList<>();
        for (int i = 1; i < size+1; i++) {
            slots.add(new LoadBufferSlot(i));
        }
    }

    public ArrayList<LoadBufferSlot> getSlots() {
        return slots;
    }

    public LoadBufferSlot getSlotByTag(String tag) {
        for (LoadBufferSlot slot : slots) {
            if (slot.getTag().equals(tag)) {
                return slot;
            }
        }
        return null;
    }

    public void clear() {
        for (LoadBufferSlot slot : slots) {
            slot.clear();
        }
    }

    public void decrementCycles() {
        for (LoadBufferSlot slot : slots) {
            slot.decrementCycle();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LoadBufferSlot slot : slots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }

    public LoadBufferSlot getFreeSlot() {
        for (LoadBufferSlot slot : slots) {
            if (slot.isFree()) {
                return slot;
            }
        }
        return null;
    }

    public boolean isExecuting(){
        for(LoadBufferSlot slot : slots){
            if(slot.isExecuting()){
                return true;
            }
        }
        return false;
    }

    public LoadBufferSlot getExecutingSlot(){
        for(LoadBufferSlot slot : slots){
            if(slot.isExecuting()){
                return slot;
            }
        }
        return null;
    }

    public LoadBufferSlot getEarliestIssueTime(){
        int min = Integer.MAX_VALUE;
        LoadBufferSlot earliest = null;
        for(LoadBufferSlot slot : slots){
            if(slot.getIssueClockCycle() < min && slot.isBusy()){
                min = slot.getIssueClockCycle();
                earliest = slot;
            }
        }
        return earliest;
    }



}
