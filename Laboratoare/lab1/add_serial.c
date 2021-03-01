//Savu Ioana Rusalda 335CB
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

/*
    schelet pentru exercitiul 5
*/
//real  0m4.570s  - 2 threads
//real    0m4.312s  -4 threads
//real    0m5.173s  -without threads


//real    0m14.930s  without threads
//real    0m8.725s  2 threads
//real    0m6.717s   4 threads

#define NUM_THREADS 2
int* arr;
int array_size;

int min(int a , int b) {
	if (a > b)
		return b;
	return a;
}

void *f(void *arg) {

	long ID = (long) arg;
	int start = ID * (double)array_size/ NUM_THREADS;
	int end = min((ID + 1) * (double)array_size / NUM_THREADS,array_size);

	//printf("%d %d %ld\n",start, end,ID );
	for (int j = 0; j < 1000; j++) {
		for (int i = start; i < end; i++) {
	        arr[i] += 100;
	    }
	}


  	pthread_exit(NULL);
}



int main(int argc, char *argv[]) {
	int r;
  	long id;
  	void *status;
  	pthread_t threads[NUM_THREADS];
    pthread_barrier_init(&barrier, NULL, P);

    if (argc < 2) {
        perror("Specificati dimensiunea array-ului\n");
        exit(-1);
    }

    array_size = atoi(argv[1]);

    arr = malloc(array_size * sizeof(int));
    for (int i = 0; i < array_size; i++) {
        arr[i] = i;
    }

    // for (int i = 0; i < array_size; i++) {
    //     printf("%d", arr[i]);
    //     if (i != array_size - 1) {
    //         printf(" ");
    //     } else {
    //         printf("\n");
    //     }
    // }

    // TODO: aceasta operatie va fi paralelizata
    // for (int j = 0; j < 1000; j++) {
  		// for (int i = 0; i < array_size; i++) {
    //    		arr[i] += 100;
    // 	}
    // }
    for (id = 0; id < NUM_THREADS; id++) {
		r = pthread_create(&threads[id], NULL, f, (void *)id);

		if (r) {
	  		printf("Eroare la crearea thread-ului %ld\n", id);
	  		exit(-1);
		}
	}
	for (id = 0; id < NUM_THREADS; id++) {
		r = pthread_join(threads[id], &status);

		if (r) {
	  		printf("Eroare la asteptarea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	}





    // for (int i = 0; i < array_size; i++) {
    //     printf("%d", arr[i]);
    //     if (i != array_size - 1) {
    //         printf(" ");
    //     } else {
    //         printf("\n");
    //     }
    // }

  	pthread_exit(NULL);
}
