package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;

import java.util.concurrent.CyclicBarrier;
import java.util.*;

public class Railroad implements Intersection {
    // Define your variables here.
   private Queue<Integer> queue = new LinkedList<Integer>();
   private CyclicBarrier barrier;

   public void setBarrier(int nr) {
      barrier = new CyclicBarrier(nr);
   }
   public CyclicBarrier  getBarrier() {
        return  this.barrier;
    }
    public Queue<Integer>  getQueue() {
       return this.queue;
    }

}
