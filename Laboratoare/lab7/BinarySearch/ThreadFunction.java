package BinarySearch;

public class ThreadFunction implements Runnable{
    private int id;
    private int x;
    volatile static  boolean found = false;

    ThreadFunction(){}
    ThreadFunction(int id,int x) {
        this.id = id;
        this.x = x;
    }
    public void run() {
        int s,e;
        int start =  this.id * Main.N/ Main.Number_of_Threads;
        int end = Math.min((this.id + 1) * Main.N / Main.Number_of_Threads,Main.N - 1);
        int n;
        s = start;
        e = end;
        //System.out.println(end);
       while (found == false) {
        //asteapta sa intre toate threadurile in while
           try {
               Main.barrier.await();
           } catch(Exception E){
               E.printStackTrace();
           }
                if (Main.array[start] == x) {
                    System.out.println("X found at position " + start +" by thread " + id);
                    found = true;

                }
                if (Main.array[end] == x ) {
                    System.out.println("X found at position " + end + " by thread " + id);
                    found = true;
                }
                if (Main.array[start] < x && Main.array[end] > x) {
                    s = start;
                    e = end;
                }

            try {
                Main.barrier.await();
            } catch(Exception E){
                E.printStackTrace();
            }
            if (found == false) {
                n = e - s;
                start = this.id * n / Main.Number_of_Threads + s;
                end = Math.min((this.id + 1) * n / Main.Number_of_Threads, n) + s;
            }

       }

    }
    public int getThreadId() {
        return id;
    }

}
