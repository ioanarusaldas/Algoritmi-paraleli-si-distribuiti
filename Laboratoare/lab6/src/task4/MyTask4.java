package task4;

import example.sampleForkJoinPool.MyTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTask4 extends RecursiveTask<Void> {
    ArrayList<Integer> partialPath;
    int destination;

    public MyTask4(ArrayList<Integer> path, int destination) {
        this.partialPath = path;
        this.destination = destination;
    }

    @Override
    protected Void compute() {
        if (partialPath.get(partialPath.size() - 1) == destination) {
            System.out.println(partialPath);
           // return;
        }

        // se verifica nodurile pentru a evita ciclarea in graf
        int lastNodeInPath = partialPath.get(partialPath.size() - 1);
        List<MyTask4> tasks = new ArrayList<>();
        for (int[] ints : Main.graph) {
            if (ints[0] == lastNodeInPath) {
                if (partialPath.contains(ints[1]))
                    continue;
               // tasks = new ArrayList<>();
                ArrayList<Integer> newPartialPath = new ArrayList<>(partialPath);
                newPartialPath.add(ints[1]);
                MyTask4 t = new MyTask4(newPartialPath,destination);
                tasks.add(t);
                t.fork();
               // getPath(newPartialPath, destination);
            }
        }
        for (MyTask4 task: tasks) {
            task.join();
        }
        return null;
    }
}
