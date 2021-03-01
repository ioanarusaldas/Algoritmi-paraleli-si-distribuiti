package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.intersections.*;
import com.apd.tema2.utils.Constants;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.concurrent.BrokenBarrierException;
import java.util.*;
/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler (String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    //masina a ajuns la semafor
                    System.out.println("Car " +car.getId()+ " has reached the semaphore, now waiting...");
                    try{
                        //masina asteapta la semafor timpul scris in fisierul de intrare
                        Thread.sleep(car.getWaitingTime());
                    }
                    catch(InterruptedException e){
                        System.out.println("Thread interrupted in simple_semaphore");
                    }
                    //masina iese din intersectie
                    System.out.println("Car " +car.getId()+ " has waited enough, now driving...");
                }
            };
            case "simple_n_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    SimpleNRoundabout i = ((SimpleNRoundabout) Main.intersection);
                    //masina ajunge la intersectie
                    System.out.println("Car " +car.getId()+ " has reached the roundabout, now waiting...");
                    //masina verifica daca poate intra in intersectie
                    try{
                        (i.getSemaphore()).acquire();
                    }
                    catch(InterruptedException e){
                        System.out.println("Thread interrupted in simple_n_roundabout");
                    }
                    //masina intra in intersectie
                    System.out.println("Car " +car.getId()+ " has entered the roundabout");
                    //masina sta in intersectie in functie de timpul primit in fisierul de intrare
                    try {
                        Thread.sleep(i.getWaitingTime());
                    }                    catch(InterruptedException e){
                        System.out.println("Thread interrupted in simple_n_roundabout");
                    }
                    //masina iese din intersectie
                    System.out.println("Car " +car.getId()+ " has exited the roundabout after " +
                                                        i.getWaitingTime() / 1000+" seconds");
                    //masina marcheaza faptul ca a iesit din intersectie
                    (i.getSemaphore()).release();


                }
            };
            case "simple_strict_1_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                   System.out.println("Car " +car.getId()+ " has reached the roundabout");
                   SimpleStrict1CarRoundabout i = ((SimpleStrict1CarRoundabout) Main.intersection);
                try{
                        i.getDirections().get(car.getStartDirection()).acquire();
                        System.out.println("Car " +car.getId()+ " has entered the roundabout from lane " +car.getStartDirection() );
                        Thread.sleep(car.getWaitingTime());
                        System.out.println("Car " +car.getId()+ " has exited the roundabout after " +  (i.getWaitingTime() / 1000) + " seconds");
                        i.getDirections().get(car.getStartDirection()).release();
                    }
                    catch(InterruptedException e){
                        System.out.println("Thread interrupted in simple_strict_1_car_roundabout");
                    }
                }
            };
            case "simple_strict_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                   try {
                        SimpleStrictXCarRoundabout i = ((SimpleStrictXCarRoundabout) Main.intersection);
                        System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");
                        //masina asteapta ca toate masinile sa ajunga la intersectie
                        try {
                           i.getBarrier1().await();
                        }
                        catch(InterruptedException | BrokenBarrierException e) {
                            System.out.println("Barrier interrupted in simple_strict_x_car_roundabout ");
                        }
                        //masina marcheaza faptul ca urmeaza sa intre in intersectie
                        i.getDirections().get(car.getStartDirection()).acquire();
                        System.out.println("Car " + car.getId() + " was selected to enter the roundabout from lane " +
                                                                        car.getStartDirection());

                        //masina asteapta sa se stranga cate x masini pe fiecare linie
                        try {
                            i.getBarrier2().await();
                        }
                        catch(InterruptedException | BrokenBarrierException e) {
                            System.out.println("Barrier2 interrupted in simple_strict_x_car_roundabout ");
                        }
                        //masina intra in intersectie
                        System.out.println("Car " + car.getId() + " has entered the roundabout from lane " +
                                                                        car.getStartDirection());
                        //masina asteapta timpul primit in fisierul de input
                        Thread.sleep(i.getWaitingTime());
                        //masina iese din intersectie
                        System.out.println("Car " + car.getId() + " has exited the roundabout after " +
                                                                                i.getWaitingTime() / 1000 + " seconds");
                        //masina asteapta ca toate masinile sa iasa din intersectie
                        try {
                            i.getBarrier2().await();
                        }
                        catch(InterruptedException | BrokenBarrierException e) {
                            System.out.println("Barrier2 interrupted in simple_strict_x_car_roundabout ");
                        }
                        //masina marcheaza faptul ca a iesit din intersetie
                        i.getDirections().get(car.getStartDirection()).release();
                    }
                    catch(InterruptedException e) {
                        System.out.println("Thread interrupted in simple_strict_x_car_roundabout");
                    }
                }
            };
            case "simple_max_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance
                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici
                    SimpleMaxXCarRoundabout i = ((SimpleMaxXCarRoundabout) Main.intersection);
                    //masina ajunge la intersectie
                    System.out.println("Car " + car.getId() + " has reached the roundabout from lane " +
                                                                                    car.getStartDirection());
                    try{
                        //masina marcheaza ca a intrat in intersectie
                        i.getDirections().get(car.getStartDirection()).acquire();
                        //masina a intrat in intersectie
                        System.out.println("Car " +car.getId()+ " has entered the roundabout from lane " +
                                                                                    car.getStartDirection());
                        Thread.sleep(car.getWaitingTime());
                        //masina a iesit din intersectie
                        System.out.println("Car " +car.getId()+ " has exited the roundabout after " +
                                                                            (i.getWaitingTime() / 1000 ) + " seconds");
                        //masina marcheaza faptul ca a iesit din intersectie
                        i.getDirections().get(car.getStartDirection()).release();
                    }
                    catch(InterruptedException e){
                        System.out.println("Thread interrupted in simple_max_x_car_roundabout");
                    }
                }
            };
            case "priority_intersection" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance
                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    PriorityIntersection i = (( PriorityIntersection) Main.intersection);
                    //verificare prioritate masina
                    if(car.getPriority() == 1){
                        //masina incearca sa intre in intersectie si e adaugat in coada
                        i.low_trying_enter(car.getId());
                        //verificare intersectie goala
                        while(i.cars_in_intersection.isEmpty() ==  false) {
                            //daca intersectia are masin, masina cu prioritate mica asteapta
                            synchronized (i.lock) {
                                try{
                                    i.lock.wait();
                                }catch(InterruptedException e) {
                                    System.out.println("Wait interrupt in priority_intersection");
                                }
                            }
                        }
                        //masina cu prioritate mica intra in intersectie
                        i.low_entered();
                    } else {
                        //masina cu prioritate mare intra in intersectie
                        i.high_enter(car.getId());
                        try {
                            //masina asteapta tipul dat ca parametru in fisierul de input
                            Thread.sleep(2000);
                            //masina iese din intersectie
                            i.high_exit(car.getId());
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted in priority_intersection");
                        }
                    }
                }
            };
            case "crosswalk" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    Crosswalk i = ((Crosswalk) Main.intersection);
                    String message;
                    while (!Main.pedestrians.isFinished()) {
                        //verificare trecere pietoni
                        if (Main.pedestrians.isPass()) {
                            synchronized (i.getLight()) {
                                message = "Car " + car.getId() + " has now red light";
                                //verificare schimbare mesaj
                                if (i.getLight().contains(message) == false) {
                                    //setare mesaj nou
                                    i.setLight("Car " + car.getId() + " has now red light", car.getId());
                                    System.out.println("Car " + car.getId() + " has now red light");
                                }
                            }

                        } else {
                            synchronized (i.getLight()) {
                                message = "Car " + car.getId() + " has now green light";
                                //verificare schimbare mesaj
                                if (i.getLight().contains(message) == false) {
                                    //setare mesaj nou
                                    i.setLight("Car " + car.getId() + " has now green light", car.getId());
                                    System.out.println("Car " + car.getId() + " has now green light");
                                }
                            }

                        }
                    }
                    //trecere masini ramase cand isFinished trece pe "true"
                    synchronized (i.getLight()) {
                        message = "Car " + car.getId() + " has now green light";
                        if (i.getLight().contains(message) == false) {
                            i.setLight("Car " + car.getId() + " has now green light", car.getId());
                            System.out.println("Car " + car.getId() + " has now green light");
                        }
                    }
                }
            };
            case "simple_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    /*SimpleMaintenance r = ((SimpleMaintenance) Main.intersection);
                    int ok = 0;
                    if (car.getStartDirection() == 0) {
                       // r.add_direction_0(car.getId());
                        r.dir_0.add(car.getId());
                        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection() + " has reached the bottleneck");

                        if (r.size_direction_0() < r.xi || r.size_direction_1() < r.x) {
                            try {
                                r.lock.wait();
                            } catch (InterruptedException e) {
                                System.out.println("interrupt");
                            }

                        }

                        if((r.size_direction_0() == r.x) && (r.size_direction_1() == r.x) ) {
                            //System.out.println("Car " +  r.direction_0.poll() + " from side number " + 0 + " has passed the bottleneck");
                            // System.out.println("Car " +  r.direction_1.poll() + " from side number " + 1 + " has passed the bottleneck");
                            //System.out.println("if");
                           // r.remove_direction_0();
                           // r.remove_direction_1();s
                            r.lock.notifyAll();
                        }
                    }
                    if (car.getStartDirection() == 1) {
                        r.add_direction_1(car.getId());
                        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection() + " has reached the bottleneck");
                        ok = 0;
                        synchronized (r.direction_0) {
                            if (r.size_direction_0() < r.x)
                                ok = 1;
                        }
                        synchronized (r.direction_1) {
                            if (r.size_direction_1() < r.x)
                                ok = 1;
                        }
                        //if (r.size_direction_0() < r.x || r.size_direction_1() < r.x) {
                        if(ok == 1){
                            synchronized (r.lock) {
                                try {
                                    r.lock.wait();
                                } catch (InterruptedException e) {
                                    System.out.println("interrupt");
                                }
                            }
                        }
                        ok = 0;
                        synchronized (r.direction_0) {
                            if (r.size_direction_0() == r.x)
                                ok ++;
                        }
                        synchronized (r.direction_1) {
                            if (r.size_direction_1() == r.x)
                                ok ++;
                        }
                        //  if((r.size_direction_0() == r.x) && (r.size_direction_1() == r.x) ) {
                        if(ok == 2){
                            //System.out.println("Car " +  r.direction_0.poll() + " from side number " + 0 + " has passed the bottleneck");
                            // System.out.println("Car " +  r.direction_1.poll() + " from side number " + 1 + " has passed the bottleneck");
                            System.out.println("if");
                            r.remove_direction_0();
                            r.remove_direction_1();
                            r.lock.notifyAll();
                        }
                    }*/


                }
            };
            case "complex_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                }
            };
            case "railroad" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    Railroad i = ((Railroad) Main.intersection);
                    try {
                        //masina ajunge la bariera
                        System.out.println("Car " +car.getId()+" from side number "+car.getStartDirection() +
                                                                                    " has stopped by the railroad");
                        i.getQueue().add(car.getId());
                        i.getQueue().add(car.getStartDirection());
                        i.getBarrier().await();
                    }
                    catch(InterruptedException | BrokenBarrierException e) {
                        System.out.println("Barrier interrupted in railroad");
                    }
                    //masina cu id 0 afuiseaza ca a trecut trenul
                    if(car.getId() ==  0) {
                        System.out.println("The train has passed, cars can now proceed");
                    }
                    try {
                        i.getBarrier().await();
                    }
                    catch(InterruptedException | BrokenBarrierException e) {
                        System.out.println("Barrier interrupted in railroad");
                    }
                    //masina trece
                    synchronized (i.getQueue()) {
                        System.out.println("Car " + i.getQueue().poll() + " from side number "+i.getQueue().poll()+
                                                                        " has started driving");
                    }
                }
            };
            default -> null;
        };
    }
}
