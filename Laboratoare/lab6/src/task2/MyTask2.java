package task2;


import example.sampleExecutorService.MyRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTask2 implements Runnable {

    //  String path;
    ArrayList<Integer> partialPath;
    ExecutorService tpe;
    int step;
    AtomicInteger inQueue;
    int[] colors;

    public MyTask2(int[] colors,int step,ExecutorService tpe, AtomicInteger inQueue) {
        this.tpe = tpe;
        this.colors = colors;
        this.inQueue = inQueue;
        this.step = step;
    }

    @Override
    public void run() {
        if (step == Main.N) {
            Main.printColors(colors);
            int left = inQueue.decrementAndGet();
            if (left == 0) {
                tpe.shutdown();
            }
        }
        // for the node at position step try all possible colors
        for (int i = 0; i < Main.COLORS; i++) {
            int[] newColors = colors.clone();
            newColors[step] = i;
            if (Main.verifyColors(newColors, step)) {
                //colorGraph(newColors, step + 1);
                inQueue.incrementAndGet();
                tpe.submit(new MyTask2 (newColors,step + 1 ,tpe, inQueue));
            }
        }
        int left = inQueue.decrementAndGet();
        if (left == 0) {
            tpe.shutdown();
        }
    }
}
