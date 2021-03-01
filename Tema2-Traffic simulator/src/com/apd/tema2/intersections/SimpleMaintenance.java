package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.*;
import java.util.*;


public class SimpleMaintenance implements Intersection {
    // Define your variables here
    public Queue<Integer> direction_0 = new LinkedList<Integer>();
    public Queue<Integer> direction_1 = new LinkedList<Integer>();
    public  Vector <Integer> dir_0;
    public  Vector <Integer> dir_1;

   // public CyclicBarrier barrier;
    public int x;
  // public ArrayList<Integer> cars_in_intersection = new ArrayList<Integer>();
  // public  String lock = "Lock";
/*  public void setBarrier(int nr) {
      barrier = new CyclicBarrier(nr);

  }*//*
   public void setVector(int nr) {
       this.dir_0 = new Vector(nr);
       this.dir_1 = new Vector(nr);
  }
    public Object lock = new Object();

    public void setX(int x) {
        this.x = x;

    }
    public synchronized void add_direction_0(int id) {
        direction_0.add(id);
    }
    public synchronized void remove_direction_0() {
        System.out.println("Car " + direction_0.poll() + " from side number " + 0 + " has passed the bottleneck");
    }
    public synchronized void add_direction_1(int id) {
        direction_1.add(id);
    }
    public synchronized void remove_direction_1() {
            System.out.println("Car " + direction_1.poll() + " from side number " + 1 + " has passed the bottleneck");
    }
    public synchronized int size_direction_0() {
        return direction_0.size();
    }
    public synchronized int size_direction_1() {
        return direction_1.size();
    }
    public synchronized verify() {
        if ((r.size_direction_0() < r.x) || (r.size_direction_1() < r.x)){
            synchronized (r.lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupt");
                }
            }
        }
    }*/

}
