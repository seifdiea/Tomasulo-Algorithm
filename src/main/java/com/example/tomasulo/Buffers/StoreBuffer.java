package com.example.tomasulo.Buffers;

import java.util.ArrayList;

public class StoreBuffer {
    private ArrayList<StoreBufferSlot> slots;

    public StoreBuffer(int size) { // in our case it should be 3
        slots = new ArrayList<>();
        for (int i = 1; i < size+1; i++) {
            slots.add(new StoreBufferSlot(i));
        }
    }

    public ArrayList<StoreBufferSlot> getSlots() {
        return slots;
    }

    public StoreBufferSlot getSlotByTag(String tag) {
        for (StoreBufferSlot slot : slots) {
            if (slot.getTag().equals(tag)) {
                return slot;
            }
        }
        return null;
    }

    public void clear() {
        for (StoreBufferSlot slot : slots) {
            slot.clear();
        }
    }

    public void decrementCycles() {
        for (StoreBufferSlot slot : slots) {
            slot.decrementCycle();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (StoreBufferSlot slot : slots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }

    public void broadcast(String tag, String result) {
        for (StoreBufferSlot slot : slots) {
            slot.broadcast(tag, result);
        }
    }

    public StoreBufferSlot getFreeSlot() {
        for (StoreBufferSlot slot : slots) {
            if (slot.isFree()) {
                return slot;
            }
        }
        return null;
    }

    public boolean isExecuting(){
        for(StoreBufferSlot slot : slots){
            if(slot.isExecuting()){
                return true;
            }
        }
        return false;
    }

    public StoreBufferSlot getExecutingSlot(){
        for(StoreBufferSlot slot : slots){
            if(slot.isExecuting()){
                return slot;
            }
        }
        return null;
    }

    public StoreBufferSlot getEarliestIssueTime(){
        int min = Integer.MAX_VALUE;
        StoreBufferSlot earliest = null;
        for(StoreBufferSlot slot : slots){
            if(slot.getIssueClockCycle() < min && slot.isBusy()){
                min = slot.getIssueClockCycle();
                earliest = slot;
            }
        }
        return earliest;
    }
}
