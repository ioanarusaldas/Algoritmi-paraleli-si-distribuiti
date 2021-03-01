package Hello;

public class Main {
    public static void main(String[] args) {
        int cores;
        cores = Runtime.getRuntime().availableProcessors();
        int NUMBER_OF_THREADS = cores;
        HelloThreads[] t = new HelloThreads[NUMBER_OF_THREADS];

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            t[i] = new HelloThreads(i);
            t[i].start();
        }

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            try {
                t[i].join();
                System.out.println("Thread with id "+ t[i].getId() + " joined");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
