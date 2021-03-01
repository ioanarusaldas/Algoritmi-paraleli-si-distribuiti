package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;
import java.util.*;

public class SimpleMaxXCarRoundabout implements Intersection {
    // Define your variables here.
    private ArrayList <Semaphore> directions;
    private int waitingTime;
    private  int x;

    public void setDirections(int dir) {
        this.directions = new ArrayList <Semaphore>() ;
        for (int i = 0; i < dir; i++) {
            directions.add(new Semaphore(x));
        }

    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public void setX(int x) {
        this.x = x;
    }
    public ArrayList<Semaphore> getDirections() {
        return this.directions;
    }
    public int getWaitingTime() {
        return this.waitingTime;
    }
    public int getX() {
        return this.x;

    }
}
