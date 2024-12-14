package com.example.tomasulo.ReservationStations;

import com.example.tomasulo.ExecutionQueue.ExecutionResult;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AdderReservationStation {

    private ArrayList<AdderRsSlot> slots;

    public AdderReservationStation(int size) { // in our case it should be 3
        slots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            slots.add(new AdderRsSlot());
        }
    }

    public ArrayList<AdderRsSlot> getSlots() {
        return slots;
    }

    public AdderRsSlot getFreeSlot() {
        for (AdderRsSlot slot : slots) {
            if (slot.isFree()) {
                return slot;
            }
        }
        return null;
    }

    public AdderRsSlot getSlotByTag(String tag) {
        for (AdderRsSlot slot : slots) {
            if (slot.getTag().equals(tag)) {
                return slot;
            }
        }
        return null;
    }

    public void broadcast(String tag, String result) {
        for (AdderRsSlot slot : slots) {
            slot.broadcast(tag, result);
        }
    }

    public void clear() {
        for (AdderRsSlot slot : slots) {
            slot.clear();
        }
    }

    public void decrementCycles() {
        for (AdderRsSlot slot : slots) {
            slot.decrementCycle();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AdderRsSlot slot : slots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }

    public PriorityQueue<ExecutionResult> execute(){
        PriorityQueue<ExecutionResult> results = new PriorityQueue<>();
        for(AdderRsSlot slot : slots){
            ExecutionResult result = slot.execute();
            if(result != null){
                results.add(result);
            }
        }
        return results;
    }

}
