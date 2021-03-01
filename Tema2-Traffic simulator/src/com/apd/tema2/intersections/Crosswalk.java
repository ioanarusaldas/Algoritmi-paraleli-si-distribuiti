package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;

import java.util.concurrent.CyclicBarrier;
import java.util.*;

public class Crosswalk implements Intersection {
    // Define your variables here.
   private ArrayList<String>  lights = new ArrayList<String>();
    public void setLight(String light,int id) {
        this.lights.set(id,light);
    }
    public void initializeLight(int nr) {
      for(int i = 0; i < nr; i++) {
          this.lights.add("null");
      }

    }
    public ArrayList<String> getLight() {
        return this.lights;
    }

}
