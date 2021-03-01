package task5;

import example.sampleForkJoinPool.MyTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTask5 extends RecursiveTask<Void> {
    int[] colors;
    int step;

    public MyTask5( int[] colors, int step) {
        this.colors = colors;
        this.step = step;
    }

    @Override
    protected Void compute() {
        if (step == Main.N) {
            Main.printColors(colors);
            return null;
        }

        // for the node at position step try all possible colors
        List<MyTask5> tasks = new ArrayList<>();
        for (int i = 0; i < Main.COLORS; i++) {
            int[] newColors = colors.clone();
            newColors[step] = i;
            if (Main.verifyColors(newColors, step)) {
                MyTask5 t = new MyTask5(newColors,step +  1);
                tasks.add(t);
                t.fork();
            }
                //colorGraph(newColors, step + 1);
        }
        for (MyTask5 task: tasks) {
            task.join();
        }
        return null;
    }
}
