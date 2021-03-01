package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;

public class SimpleNRoundabout implements Intersection {
    // Define your variables here.
    private Semaphore semaphore;
    private int waitingTime;

    public void setSemaphore(int acceptedCars) {
        this.semaphore = new Semaphore(acceptedCars);
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public Semaphore getSemaphore() {
        return this.semaphore;
    }
    public int getWaitingTime() {
        return this.waitingTime;
    }
}
