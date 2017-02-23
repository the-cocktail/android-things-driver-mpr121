MPR121 driver for Android Things
================================

This driver supports peripherals built on the MPR121 SPI protocol.

NOTE: these drivers are not production-ready. They are offered as sample
implementations of Android Things. 
There is no guarantee of correctness, completeness or robustness.

How to use the driver
---------------------

### Gradle dependency

To use the `mpr121` driver, simply add the line below to your project's `build.gradle`,
where `<version>` matches the last version of the driver available on [jcenter][jcenter].

```
dependencies {
    compile 'com.tck.android.things:driver-mpr121:<version>'
}
```

### Sample usage

```java
import com.tck.android.things.drivers.mpr121;


Mpr121 mMpr121;


// Open device and set TouchEvent callback
try {
    mMpr121 = new Mpr121(busName);
    mMpr121.addTouchListener(new TouchListener() {
        @Override
        public void touched(TouchEvent e) {
            Log.d(TAG, "TOUCHED " + e.getElectrode().name());
        }
        @Override
        public void released(TouchEvent e) {
            Log.d(TAG, "RELEASED " + e.getElectrode().name());
        }
    });
    mMpr121.start();
} catch (IOException e) {
    e.printStackTrace();
}


// Close when finished
mpr121.stop();
try {
    mpr121.close();
} catch (IOException e) {
    // error closing
}
```

License
-------

This software is written by The Cocktail.
Licensed under The GPL License V3.

Contributing to this software is welcomed. You can do this by forking, committing modifications and then pulling requests.

Thanks for your contribution.