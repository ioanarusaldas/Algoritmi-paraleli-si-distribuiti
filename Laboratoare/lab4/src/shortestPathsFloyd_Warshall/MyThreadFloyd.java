package shortestPathsFloyd_Warshall;


import java.util.concurrent.BrokenBarrierException;

public class MyThreadFloyd extends Thread {
    private int  id;
    private int P;
    private int N;

    public MyThreadFloyd(int id,int P,int N) {
        this.id = id;
        this.P = P;
        this.N = N;
    }
    @Override
    public void run() {
      //  int iterationCounter = 0;
        int start =  this.id * N/ P;
        int end = Math.min((this.id + 1) * N / P, N);

//        for (int k = 0; k < N; k++) {
//            for (int i = start; i < end; i++) {
//                for (int j = 0; j < N; j++) {
//                    Main.graph[i][j] = Math.min(Main.graph[i][k] + Main.graph[k][j], Main.graph[i][j]);
//                }
//            }
//        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = start; j < end; j++) {
                    Main.graph[i][j] = Math.min(Main.graph[i][k] + Main.graph[k][j], Main.graph[i][j]);
                }
                try {
                    //Resincronizarea thread-urilor pentru urmatorul pas al algoritmului.
                    Main.barrier.await();
                    //++iterationCounter;
                } catch (BrokenBarrierException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
