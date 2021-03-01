package shortestPathsFloyd_Warshall;

import doubleVectorElements.MyThread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author cristian.chilipirea
 *
 */
public class Main {
	public static int M = 9;
	public static int N = 5;
	public static int graph[][] = { { 0, 1, M, M, M },
			{ 1, 0, 1, M, M },
			{ M, 1, 0, 1, 1 },
			{ M, M, 1, 0, M },
			{ M, M, 1, M, 0 } };
//	public static int N = 8;
//	public static int M = 1000;
//		public static int graph[][] = { { M, M, M, 3, M ,M, M, M},
//			{ M, M, 1, M, M, M, 3, M },
//			{ 5, M, M, M, M, M, M, M },
//				{ M, 9, M, M, M ,9, M, 2},
//				{ M, M, M, M, M ,M, M, 8},
//				{ M, 6, M, 1, M ,M, 3, M},
//				{ M, M, 1, M, M ,M, M, M},
//				{ M, M, M, M, M ,M, M, M}};

	public static CyclicBarrier barrier;
	public static void main(String[] args) {
		int cores = 2;
		//cores = Runtime.getRuntime().availableProcessors();
		int NUMBER_OF_THREADS = cores;

		barrier = new CyclicBarrier(NUMBER_OF_THREADS);
 		int graph_aux[][] = new int[N][N];
 		for (int i = 0; i < N; i++ ) {
			for (int j = 0; j < N; j++) {
				graph_aux[i][j] = graph[i][j];
			}

		}
		
		// Parallelize me (You might want to keep the original code in order to compare)
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					graph_aux[i][j] = Math.min(graph_aux[i][k] + graph_aux[k][j], graph_aux[i][j]);
				}
			}
		}

		MyThreadFloyd[] t = new MyThreadFloyd[NUMBER_OF_THREADS];

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			t[i] = new MyThreadFloyd(i,NUMBER_OF_THREADS,N);
			t[i].start();
		}

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}



		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(graph_aux[i][j] != graph[i][j]) {
					System.out.println("incorect");
					System.exit(0);
				}
			}
		}

		System.out.println("corect");
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(graph_aux[i][j]+" ");
//			}
//			System.out.println();
//		}


	}
}
