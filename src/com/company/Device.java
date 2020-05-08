package com.company;

/**
 * This is an Interface for Device
 */
public interface Device {
    enum Status {
        ON, OFF, HOVERING, MOVING
    }

    Status getDeviceStatus();
    void setDeviceStatus(Status status);
}
