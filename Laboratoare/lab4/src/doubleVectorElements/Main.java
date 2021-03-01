package doubleVectorElements;

import Hello.HelloThreads;

/**
 * @author cristian.chilipirea
 *
 */
public class Main {
	public static int N = 100000013;
	//public static int N = 20;
	public static int v[] = new int[N];
	public static void main(String[] args) {

		for(int i=0;i<N;i++)
			v[i]=i;
		
		// Parallelize me
//		for (int i = 0; i < N; i++) {
//			v[i] = v[i] * 2;
//		}
		int cores;
		cores = Runtime.getRuntime().availableProcessors();
		int NUMBER_OF_THREADS = cores;
		MyThread[] t = new MyThread[NUMBER_OF_THREADS];

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			t[i] = new MyThread(i,NUMBER_OF_THREADS);
			t[i].start();
		}

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//verific realizarea corecta a dublarii
		for (int i = 0; i < N; i++) {
			if(v[i]!= i*2) {
				System.out.println("Wrong answer");
				System.exit(1);
			}
		}
		System.out.println("Correct");
	}

}
