package BinarySearch;

import multipleProducersMultipleConsumers.Producer;

import java.util.concurrent.CyclicBarrier;

public class Main {

        public static int N = 20;
        public static int array[] = new int[N];
        public static int Number_of_Threads = 4;
        public static CyclicBarrier barrier;
         public static void main(String[] args) {
            barrier = new CyclicBarrier(Number_of_Threads);
            for(int i = 0; i < N; i++){
                array[i] = i;
            }


            Thread threads[] = new Thread[Number_of_Threads];
            for (int i = 0; i < Number_of_Threads; i++)
                threads[i] = new Thread(new ThreadFunction(i,10));
            for (int i = 0; i < Number_of_Threads; i++)
                threads[i].start();
            for (int i = 0; i < Number_of_Threads; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

}
