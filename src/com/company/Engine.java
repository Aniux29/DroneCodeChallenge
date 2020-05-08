package com.company;

/**
 * This is the class for the device Engine of the Drone
 */
public class Engine implements Device {
    private double powerIndicator;

    public double getPowerIndicator(){
        return powerIndicator;
    }

    public void setPowerIndicator(double newValue){
        if(newValue < 0 || newValue > 100 ){
            throw new IllegalArgumentException();
        } else {
            this.powerIndicator = newValue;
        }

    }

    @Override
    public Status getDeviceStatus() {
        return null;
    }

    @Override
    public void setDeviceStatus(Status status) {

    }
}
