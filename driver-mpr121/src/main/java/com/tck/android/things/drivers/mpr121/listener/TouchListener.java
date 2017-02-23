package com.tck.android.things.drivers.mpr121.listener;

import com.tck.android.things.drivers.mpr121.event.TouchEvent;

public interface TouchListener {

    public void touched(TouchEvent e);

    public void released(TouchEvent e);

}