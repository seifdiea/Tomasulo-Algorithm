package com.example.tomasulo.ReservationStations;

import com.example.tomasulo.ExecutionQueue.ExecutionResult;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MultiplierReservationStation {

    private ArrayList<MultiplierRsSlot> slots;

    public MultiplierReservationStation(int size) { // in our case it should be 2
        slots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            slots.add(new MultiplierRsSlot());
        }
    }


    public MultiplierRsSlot getFreeSlot() {
        for (MultiplierRsSlot slot : slots) {
            if (slot.isFree()) {
                return slot;
            }
        }
        return null;
    }

    public MultiplierRsSlot getSlotByTag(String tag) {
        for (MultiplierRsSlot slot : slots) {
            if (slot.getTag().equals(tag)) {
                return slot;
            }
        }
        return null;
    }

    public void broadcast(String tag, String result) {
        for (MultiplierRsSlot slot : slots) {
            slot.broadcast(tag, result);
        }
    }

    public void clear() {
        for (MultiplierRsSlot slot : slots) {
            slot.clear();
        }
    }

    public void decrementCycles() {
        for (MultiplierRsSlot slot : slots) {
            slot.decrementCycle();
        }
    }

    public ArrayList<MultiplierRsSlot> getSlots() {
        return slots;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MultiplierRsSlot slot : slots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }

    public PriorityQueue<ExecutionResult> execute(){
        PriorityQueue<ExecutionResult> results = new PriorityQueue<>();
        for(MultiplierRsSlot slot : slots){
            ExecutionResult result = slot.execute();
            if(result != null){
                results.add(result);
            }
        }
        return results;
    }
}
