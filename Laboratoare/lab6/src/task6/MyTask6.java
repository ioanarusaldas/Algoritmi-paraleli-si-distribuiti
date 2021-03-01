package task6;

import example.sampleForkJoinPool.MyTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTask6 extends RecursiveTask<Void> {
    int[] graph;
    int step;

    public MyTask6( int[] graph, int step) {
        this.graph = graph;
        this.step = step;
    }

    @Override
    protected Void compute() {
        if (Main.N == step) {
            Main.printQueens(graph);
            return null;
        }
        List<MyTask6> tasks = new ArrayList<>();
        for (int i = 0; i < Main.N; ++i) {
            int[] newGraph = graph.clone();
            newGraph[step] = i;

            if (Main.check(newGraph, step)) {
               // queens(newGraph, step +
                MyTask6 t = new MyTask6(newGraph,step +  1);
                tasks.add(t);
                t.fork();
            }
        }

        for (MyTask6 task: tasks) {
            task.join();
        }
        return null;
    }
}
