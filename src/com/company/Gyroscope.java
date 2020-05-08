package com.company;

/**
 * This is a class for the gyroscope of a Drone
 */
public class Gyroscope implements Device {
    int vectorX;
    int vectorY;
    int vectorZ;

    public int getVelocityX() {
        return vectorX;
    }
    public int getVelocityY() {
        return vectorY;
    }
    public int getVelocityZ() {
        return vectorZ;
    }

    @Override
    public Status getDeviceStatus() {
        return null;
    }

    @Override
    public void setDeviceStatus(Status status) {

    }
}

