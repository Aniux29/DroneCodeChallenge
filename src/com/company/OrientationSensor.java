package com.company;

/**
 * This is a class for orientation sensor of a drone
 */
public class OrientationSensor implements Device{

    public int getPitch() {
        return 0;
    }

    public int getRoll() {
        return 0;
    }


    @Override
    public Status getDeviceStatus() {
        return null;
    }

    @Override
    public void setDeviceStatus(Status status) {

    }
}
