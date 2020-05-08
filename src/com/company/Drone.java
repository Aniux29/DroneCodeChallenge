package com.company;

import java.util.*;

/**
 * This is the class of a Drone
 */

public class Drone implements Device {


    enum EngineGroup {LEFT_GROUP, RIGHT_GROUP, FRONT_GROUP, BACK_GROUP, ALL, LEFT_TORQUE, RIGHT_TORQUE}

    private Gyroscope droneGyroscope;
    private OrientationSensor droneOrientationSensor;
    private Map<EngineGroup, Set<Engine>> mapEngine;
    private Status droneStatus;

    Drone() {

    }


    @Override
    public Device.Status getDeviceStatus() {
        return this.droneStatus;
    }

    @Override
    public void setDeviceStatus(Device.Status status) {

    }

    public void takeOff() {
        try {
            droneGyroscope.setDeviceStatus(Status.ON);
            droneOrientationSensor.setDeviceStatus(Status.ON);
            mapEngine.get(EngineGroup.ALL).iterator().forEachRemaining(engine -> engine.setDeviceStatus(Status.ON));
            droneStatus = Status.ON;
            moveUp(1);
        } catch (Exception e) {
            System.out.println("Failed to Take OFF" + e);
        }

    }

    public void moveLeft(int velocity) {
        while (droneOrientationSensor.getRoll() < 45 || droneGyroscope.getVelocityY()>velocity) {
            mapEngine.get(EngineGroup.RIGHT_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(1));
            mapEngine.get(EngineGroup.LEFT_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(-1));
            moveUp(0);
        }
        droneStatus = Status.MOVING;
    }

    public void moveRight(int velocity) {
        while (droneOrientationSensor.getRoll() > -45 || droneGyroscope.getVelocityY()<velocity) {
            mapEngine.get(EngineGroup.LEFT_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(1));
            mapEngine.get(EngineGroup.RIGHT_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(-1));
            moveUp(0);
        }
        droneStatus = Status.MOVING;
    }

    public void moveForward(int velocity) {
        while (droneOrientationSensor.getPitch() > -45 || droneGyroscope.getVelocityX()<velocity) {
            mapEngine.get(EngineGroup.FRONT_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(-1));
            mapEngine.get(EngineGroup.BACK_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(1));
            moveUp(0);
        }
        droneStatus = Status.MOVING;
    }

    public void moveBack(int velocity) {
        while (droneOrientationSensor.getPitch() < 45 || droneGyroscope.getVelocityX()>velocity) {
            mapEngine.get(EngineGroup.FRONT_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(1));
            mapEngine.get(EngineGroup.BACK_GROUP).iterator().forEachRemaining(engine -> engine.setPowerIndicator(-1));
            moveUp(0);
        }
        droneStatus = Status.MOVING;
    }

    public void moveUp(int velocity) {
        while(droneGyroscope.getVelocityZ()<velocity){
            mapEngine.get(EngineGroup.ALL).iterator().forEachRemaining(engine -> engine.setPowerIndicator(1));
        }
        droneStatus = Status.MOVING;

    }

    public void moveDown(int velocity) {
        while(droneGyroscope.getVelocityZ()>velocity){
            mapEngine.get(EngineGroup.ALL).iterator().forEachRemaining(engine -> engine.setPowerIndicator(-1));
        }
        droneStatus = Status.MOVING;

    }

    //stabilize(makes the drone hover)
    public void stabilize() {
        if(droneOrientationSensor.getPitch()>45){
            moveForward(0);
        }else{
            moveBack(0);
        }
        if(droneOrientationSensor.getRoll()>45){
            moveRight(0);
        }else{
            moveLeft(0);
        }
        droneStatus = Status.HOVERING;
    }


    //land(stabilizes and goes down at reduce speed)
    public void land(){
        stabilize();
        while(droneGyroscope.getVelocityZ()!= 0){
            moveDown(1);
        }
    }

    Thread monitorEngine = new Thread(() -> {
       while(mapEngine.get(EngineGroup.ALL).stream().allMatch(engine -> engine.getDeviceStatus().equals(Status.ON))){
       }

       if(mapEngine.get(EngineGroup.LEFT_TORQUE).stream().anyMatch(engine -> engine.getDeviceStatus().equals(Status.OFF))){
           mapEngine.get(EngineGroup.LEFT_TORQUE).iterator().forEachRemaining(engine -> engine.setPowerIndicator(2));
           mapEngine.get(EngineGroup.RIGHT_TORQUE).iterator().forEachRemaining(engine -> engine.setPowerIndicator(.5));
           System.out.println("An engine on left torque went OFF");
       }
        if(mapEngine.get(EngineGroup.RIGHT_TORQUE).stream().anyMatch(engine -> engine.getDeviceStatus().equals(Status.OFF))){
            mapEngine.get(EngineGroup.RIGHT_TORQUE).iterator().forEachRemaining(engine -> engine.setPowerIndicator(2));
            mapEngine.get(EngineGroup.LEFT_TORQUE).iterator().forEachRemaining(engine -> engine.setPowerIndicator(.5));
            System.out.println("An engine on right torque went OFF");
        }
        land();
    });


}
