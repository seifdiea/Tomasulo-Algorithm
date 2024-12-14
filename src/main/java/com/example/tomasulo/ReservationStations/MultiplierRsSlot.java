package com.example.tomasulo.ReservationStations;

public class MultiplierRsSlot extends ReservationStationSlot {

    private static int counter = 1;
    private static final String tagPrefix = "M";

    public MultiplierRsSlot() {
        super(tagPrefix, counter);
        counter++;
    }
}
