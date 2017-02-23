package com.tck.android.things.driver.mpr121.demo;

import com.google.android.things.pio.PeripheralManagerService;

import com.tck.android.things.drivers.mpr121.Mpr121;
import com.tck.android.things.drivers.mpr121.event.TouchEvent;
import com.tck.android.things.drivers.mpr121.listener.TouchListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PeripheralManagerService peripheralManager = new PeripheralManagerService();
    private Mpr121 mpr121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // I2C for MPR121 microcontroller init
        List<String> deviceList = peripheralManager.getI2cBusList();
        if (deviceList.isEmpty()) {
            Log.i(TAG, "No I2C bus available on this capacitiveDevice.");
        } else {
            Log.i(TAG, "I2C List of available devices: " + deviceList);

            try {
                mpr121 = new Mpr121(deviceList.get(0));
                mpr121.addTouchListener(new TouchListener() {
                    @Override
                    public void touched(TouchEvent e) {
                        Log.d(TAG, "TOUCHED " + e.getCapacitiveButton().name());
                    }

                    @Override
                    public void released(TouchEvent e) {
                        Log.d(TAG, "RELEASED " + e.getCapacitiveButton().name());
                    }
                });
                mpr121.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mpr121.stop();
        try {
            mpr121.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}