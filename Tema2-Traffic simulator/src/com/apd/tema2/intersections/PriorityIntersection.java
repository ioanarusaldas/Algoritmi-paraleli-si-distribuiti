package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;
import java.util.*;

public class PriorityIntersection implements Intersection {
    // Define your variables here.
   public Queue<Integer> low = new LinkedList<Integer>();
   public ArrayList<Integer> cars_in_intersection = new ArrayList<Integer>();
   public  String lock = "Lock";
   public synchronized void low_trying_enter(int id) {
        System.out.println("Car "+ id +" with low priority is trying to enter the intersection...");
        low.add(id);
   }
    public synchronized void low_entered() {
        System.out.println("Car " + low.poll() + " with low priority has entered the intersection");
    }
    public synchronized void high_enter(int id) {
        System.out.println("Car "+ id +" with high priority has entered the intersection");
        cars_in_intersection.add(id);
    }
    public synchronized void high_exit(int id) {

        System.out.println("Car " + id + " with high priority has exited the intersection");
        cars_in_intersection.remove(cars_in_intersection.indexOf(id));
        synchronized (lock) {
            lock.notifyAll();
        }
    }

}
