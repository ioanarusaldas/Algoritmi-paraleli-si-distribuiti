package task3;


import example.sampleExecutorService.MyRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTask3 implements Runnable {

    //  String path;
    ArrayList<Integer> partialPath;
    ExecutorService tpe;
    int step;
    AtomicInteger inQueue;
    int[] graph;
    public MyTask3(int[] graph,int step,ExecutorService tpe, AtomicInteger inQueue) {
        this.tpe = tpe;
        this.graph = graph;
        this.inQueue = inQueue;
        this.step = step;
    }

    @Override
    public void run() {
        if (Main.N == step) {
            Main.printQueens(graph);
            int left = inQueue.decrementAndGet();
            if (left == 0) {
                tpe.shutdown();
            }
        }
        for (int i = 0; i < Main.N; ++i) {
            int[] newGraph = graph.clone();
            newGraph[step] = i;

            if (Main.check(newGraph, step)) {
               // queens(newGraph, step + 1);
                inQueue.incrementAndGet();
                tpe.submit(new MyTask3 (newGraph,step + 1 ,tpe, inQueue));
            }
        }
        int left = inQueue.decrementAndGet();
        if (left == 0) {
            tpe.shutdown();
        }
    }
}
