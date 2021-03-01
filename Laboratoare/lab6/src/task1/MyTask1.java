package task1;


import example.sampleExecutorService.MyRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTask1 implements Runnable {

      //  String path;
        ArrayList<Integer> partialPath;
        ExecutorService tpe;
        int destination;
        AtomicInteger inQueue;

        public MyTask1(ArrayList<Integer> path, ExecutorService tpe, int destination, AtomicInteger inQueue) {
            this.partialPath = path;
            this.tpe = tpe;
            this.destination = destination;
            this.inQueue = inQueue;
        }

        @Override
        public void run() {
            if (partialPath.get(partialPath.size() - 1) == destination) {
                System.out.println(partialPath);
            }

            // se verifica nodurile pentru a evita ciclarea in graf
            int lastNodeInPath = partialPath.get(partialPath.size() - 1);
            for (int[] ints : Main.graph) {
                if (ints[0] == lastNodeInPath) {
                    if (partialPath.contains(ints[1]))
                        continue;
                    ArrayList<Integer> newPartialPath = new ArrayList<>(partialPath);
                    newPartialPath.add(ints[1]);
                    //getPath(newPartialPath, destination);
                    inQueue.incrementAndGet();
                    tpe.submit(new MyTask1(newPartialPath, tpe, destination,inQueue));
                }
            }
            int left = inQueue.decrementAndGet();
            if (left == 0) {
                tpe.shutdown();
            }
        }

}
