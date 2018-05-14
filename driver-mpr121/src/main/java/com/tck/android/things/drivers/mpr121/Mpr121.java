package com.tck.android.things.drivers.mpr121;

import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.PeripheralManager;

import com.tck.android.things.drivers.mpr121.event.TouchEvent;
import com.tck.android.things.drivers.mpr121.listener.TouchListener;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mpr121 implements AutoCloseable {

    private I2cDevice mDevice;

    private List<TouchListener> touchListeners;

    private boolean[] touchStates = new boolean[12];

    private Lock lock = new ReentrantLock();

    // Software polling delay
    private static final int SOFTWAREPOLL_DELAY_MS = 16;

    private Handler inputHandler;

    private byte touchThreshold = Mpr121Values.TOU_THRESH;
    private byte releaseRhreshold = Mpr121Values.REL_THRESH;

    /**
     * Create a new driver for a Mpr121 peripheral connected on the given I2C bus.
     */
    public Mpr121(String bus) throws IOException {
        PeripheralManager pioService = PeripheralManager.getInstance();
        I2cDevice device = pioService.openI2cDevice(bus, Mpr121Values.MPR121_ADDRESS);
        connect(device);
    }

    private void connect(I2cDevice device) throws IOException {
        mDevice = device;

        touchListeners = new ArrayList<>();
        inputHandler = new Handler(Looper.myLooper());
    }

    /**
     * Close the driver and the underlying device.
     */
    @Override
    public void close() throws IOException {
        if (mDevice != null) {
            try {
                mDevice.close();
            } finally {
                mDevice = null;
            }
        }
    }

    public void start() throws IOException {
        System.out.println("Mpr121 start");
        setup();

        inputHandler.post(mPollingCallback);

        System.out.println("Mpr121 start finishinitialized");
    }

    public void stop() {
        System.out.println("Mpr121 stop");

        inputHandler.removeCallbacks(mPollingCallback);

        System.out.println("Mpr121 stop finishinitialized");
    }

    private void setup() throws IOException {
        register(Mpr121Values.ELE_CFG, (byte) 0x00);

        // Section A - Controls filtering when data is > baseline.
        register(Mpr121Values.MHDR, (byte) 0x01);
        register(Mpr121Values.NHDR, (byte) 0x01);
        register(Mpr121Values.NCLR, (byte) 0x00);
        register(Mpr121Values.FDLR, (byte) 0x00);

        // Section B - Controls filtering when data is < baseline.
        register(Mpr121Values.MHDF, (byte) 0x01);
        register(Mpr121Values.NHDF, (byte) 0x01);
        register(Mpr121Values.NCLF, (byte) 0xFF);
        register(Mpr121Values.FDLF, (byte) 0x02);

        // Section C - Sets touch and release thresholds for each electrode
        register(Mpr121Values.E0TTH, touchThreshold);
        register(Mpr121Values.E0RTH, releaseRhreshold);

        register(Mpr121Values.E1TTH, touchThreshold);
        register(Mpr121Values.E1RTH, releaseRhreshold);

        register(Mpr121Values.E2TTH, touchThreshold);
        register(Mpr121Values.E2RTH, releaseRhreshold);

        register(Mpr121Values.E3TTH, touchThreshold);
        register(Mpr121Values.E3RTH, releaseRhreshold);

        register(Mpr121Values.E4TTH, touchThreshold);
        register(Mpr121Values.E4RTH, releaseRhreshold);

        register(Mpr121Values.E5TTH, touchThreshold);
        register(Mpr121Values.E5RTH, releaseRhreshold);

        register(Mpr121Values.E6TTH, touchThreshold);
        register(Mpr121Values.E6RTH, releaseRhreshold);

        register(Mpr121Values.E7TTH, touchThreshold);
        register(Mpr121Values.E7RTH, releaseRhreshold);

        register(Mpr121Values.E8TTH, touchThreshold);
        register(Mpr121Values.E8RTH, releaseRhreshold);

        register(Mpr121Values.E9TTH, touchThreshold);
        register(Mpr121Values.E9RTH, releaseRhreshold);

        register(Mpr121Values.E10TTH, touchThreshold);
        register(Mpr121Values.E10RTH, releaseRhreshold);

        register(Mpr121Values.E11TTH, touchThreshold);
        register(Mpr121Values.E11RTH, releaseRhreshold);

        register(Mpr121Values.E12TTH, touchThreshold);
        register(Mpr121Values.E12RTH, releaseRhreshold);

        // Section D
        // Set the Filter Configuration
        // Set ESI2
        register(Mpr121Values.FIL_CFG, (byte) 0x04);
//        register(Mpr121Values.ATO_CFGU, (byte) 0xC9); // USL = (Vdd-0.7)/vdd*256 = 0xC9 @3.3V
//        register(Mpr121Values.ATO_CFGL, (byte) 0x82); // LSL = 0.65*USL = 0x82 @3.3V
//        register(Mpr121Values.ATO_CFGT, (byte) 0xb5); // Target = 0.9*USL = 0xB5 @3.3V
//        register(Mpr121Values.ATO_CFG0, (byte) 0x1B);

        // Section E
        // CapacitiveButton Configuration
        // Set ELE_CFG to 0x00 to return to standby mode
        register(Mpr121Values.ELE_CFG, (byte) 0x0C); // Enables all 12 Electrodes

        // Section F
        // Enable Auto Config and auto Reconfig
//        register(Mpr121Values.ATO_CFG0, (byte) 0x0B);
//        register(Mpr121Values.ATO_CFGU, (byte) 0xC9); // USL = (Vdd-0.7)/vdd*256 = 0xC9 @3.3V
//        register(Mpr121Values.ATO_CFGL, (byte) 0x82); // LSL = 0.65*USL = 0x82 @3.3V
//        register(Mpr121Values.ATO_CFGT, (byte) 0xB5); // Target = 0.9*USL = 0xB5 @3.3V
    }

    private void register(byte address, byte value) throws IOException {
        mDevice.write(new byte[]{address, value}, 2);
    }

    public void addTouchListener(TouchListener l) {
        touchListeners.add(l);
    }

    private void notifyTouch(CapacitiveButton capacitiveButton) {
        TouchEvent event = new TouchEvent(capacitiveButton);
        for (TouchListener l : touchListeners) {
            l.touched(event);
        }
    }

    private void notifyRelease(CapacitiveButton capacitiveButton) {
        TouchEvent event = new TouchEvent(capacitiveButton);
        for (TouchListener l : touchListeners) {
            l.released(event);
        }
    }

    private Runnable mPollingCallback = new Runnable() {
        @Override
        public void run() {
            handleInterrupt();
            inputHandler.postDelayed(this, SOFTWAREPOLL_DELAY_MS);
        }
    };

    private void handleInterrupt() {
        try {
            lock.tryLock(100, TimeUnit.MILLISECONDS);

            byte[] reisters = new byte[42];
            mDevice.read(reisters, 42);

            byte LSB = reisters[0];
            byte MSB = reisters[1];

            int touched = ((MSB << 8) | LSB);

            for (int i = 0; i < 12; i++) {
                if ((touched & (1 << i)) != 0x00) {
                    if (!touchStates[i]) {
                        // Pin i just touched
                        notifyTouch(CapacitiveButton.values()[i]);
                    } else {
                        // Pin i still being touched
                    }
                    touchStates[i] = true;
                } else {
                    if (touchStates[i]) {
                        notifyRelease(CapacitiveButton.values()[i]);
                        // Pin i no longer being touched
                    }
                    touchStates[i] = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}