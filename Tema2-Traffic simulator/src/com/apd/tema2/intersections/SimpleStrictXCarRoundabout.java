package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CyclicBarrier;
import java.util.*;

public class SimpleStrictXCarRoundabout implements Intersection {
    // Define your variables here.
    private ArrayList <Semaphore> directions;
    private CyclicBarrier barrier;
    private CyclicBarrier barrier2;

    private int waitingTime;
    private int x;
    private int dir;

    public void setDirections(int dir) {
        this.directions = new ArrayList<Semaphore>();
        for (int i = 0; i < dir; i++) {
            directions.add(new Semaphore(x));
        }
        this.dir = dir;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;

    }
    public void setX(int x) {
        this.x = x;

    }
    public void setBarrier(int nr) {
        barrier = new CyclicBarrier(nr);
        barrier2 = new CyclicBarrier(x * dir);

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
    public CyclicBarrier  getBarrier1() {
        return  this.barrier;
    }
    public CyclicBarrier  getBarrier2() {
        return  this.barrier2;
    }
}
