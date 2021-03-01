package doubleVectorElements;


public class MyThread extends Thread {
    private int  id;
    private int P;

    public MyThread(int id,int P) {
        this.id = id;
        this.P = P;
    }
    @Override
    public void run() {

        int start =  this.id * Main.N/ P;
        int end = Math.min((this.id + 1) * Main.N / P,Main.N);
        //System.out.println(start +" "+ end);
        for (int i = start; i < end; i++) {
            Main.v[i] = Main.v[i] * 2;
        }
    }
}

