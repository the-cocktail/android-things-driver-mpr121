package com.tck.android.things.drivers.mpr121.event;

import com.tck.android.things.drivers.mpr121.CapacitiveButton;

public class TouchEvent {

    CapacitiveButton mCapacitiveButton;

    public TouchEvent(CapacitiveButton capacitiveButton) {
        super();

        this.mCapacitiveButton = capacitiveButton;
    }

    public CapacitiveButton getCapacitiveButton() {
        return mCapacitiveButton;
    }

    public void setCapacitiveButton(CapacitiveButton capacitiveButton) {
        this.mCapacitiveButton = capacitiveButton;
    }

}