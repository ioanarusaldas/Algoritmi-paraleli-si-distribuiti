package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.Semaphore;
import java.util.*;

public class SimpleStrict1CarRoundabout implements Intersection {
  private ArrayList <Semaphore> directions;
  private int waitingTime;

  public void setDirections(int dir) {
      this.directions = new ArrayList<Semaphore>();
      for (int i = 0; i < dir; i++) {
            directions.add(new Semaphore(1));
      }
  }
  public void setWaitingTime(int waitingTime) {
      this.waitingTime = waitingTime;
  }
  public ArrayList <Semaphore> getDirections() {
      return directions;
  }
  public int getWaitingTime() {
      return waitingTime;
  }
}
