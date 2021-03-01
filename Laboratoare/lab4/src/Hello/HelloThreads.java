package Hello;


public class HelloThreads extends Thread{
    private int id;

    public HelloThreads(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Hello from thread " + id);
    }

    public int getThreadId() {
        return id;
    }

}