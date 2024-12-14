package com.example.tomasulo.ReservationStations;

public class AdderRsSlot extends ReservationStationSlot {

    private static int counter = 1;
    private static final String tagPrefix = "A";

    public AdderRsSlot() {
        super(tagPrefix, counter);
        counter++;
    }


}
