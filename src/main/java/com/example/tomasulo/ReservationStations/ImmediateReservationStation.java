package com.example.tomasulo.ReservationStations;

import java.util.ArrayList;

public class ImmediateReservationStation {
    private ArrayList<ImmediateReservationStationSlot> slots;

    public ImmediateReservationStation() {
        slots = new ArrayList<ImmediateReservationStationSlot>();
    }

    public void decrementCycles() {
        for (ImmediateReservationStationSlot slot : slots) {
            slot.decrementCycle();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ImmediateReservationStationSlot slot : slots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }

    public ArrayList<ImmediateReservationStationSlot> getSlots() {
        return slots;
    }
}
