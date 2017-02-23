package com.tck.android.things.drivers.mpr121;

public interface Mpr121Values {

    // I2C address
    public static byte MPR121_ADDRESS = 0x5A;

    // Register address
    public static byte MHDR = 0x2B;
    public static byte NHDR = 0x2C;
    public static byte NCLR = 0x2D;
    public static byte FDLR = 0x2E;
    public static byte MHDF = 0x2F;
    public static byte NHDF = 0x30;
    public static byte NCLF = 0x31;
    public static byte FDLF = 0x32;
    public static byte E0TTH = 0x41;
    public static byte E0RTH = 0x42;
    public static byte E1TTH = 0x43;
    public static byte E1RTH = 0x44;
    public static byte E2TTH = 0x45;
    public static byte E2RTH = 0x46;
    public static byte E3TTH = 0x47;
    public static byte E3RTH = 0x48;
    public static byte E4TTH = 0x49;
    public static byte E4RTH = 0x4A;
    public static byte E5TTH = 0x4B;
    public static byte E5RTH = 0x4C;
    public static byte E6TTH = 0x4D;
    public static byte E6RTH = 0x4E;
    public static byte E7TTH = 0x4F;
    public static byte E7RTH = 0x50;
    public static byte E8TTH = 0x51;
    public static byte E8RTH = 0x52;
    public static byte E9TTH = 0x53;
    public static byte E9RTH = 0x54;
    public static byte E10TTH = 0x55;
    public static byte E10RTH = 0x56;
    public static byte E11TTH = 0x57;
    public static byte E11RTH = 0x58;
    public static byte E12TTH = 0x59;
    public static byte E12RTH = 0x5A;

    public static byte FIL_CFG = 0x5D;
    public static byte ELE_CFG = 0x5E;
    public static byte GPIO_CTRL0 = 0x73;
    public static byte GPIO_CTRL1 = 0x74;
    public static byte GPIO_DATA = 0x75;
    public static byte GPIO_DIR = 0x76;
    public static byte GPIO_EN = 0x77;
    public static byte GPIO_SET = 0x78;
    public static byte GPIO_CLEAR = 0x79;
    public static byte GPIO_TOGGLE = 0x7A;
    public static byte ATO_CFG0 = 0x7B;
    public static byte ATO_CFGU = 0x7D;
    public static byte ATO_CFGL = 0x7E;
    public static byte ATO_CFGT = 0x7F;

    // Global Mpr121Values
    public static byte TOU_THRESH = 0x06;
    public static byte REL_THRESH = 0x0A;

}
