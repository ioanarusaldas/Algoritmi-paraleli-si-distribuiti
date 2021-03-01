//Savu Ioana Rusalda 335CB
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define NUM_THREADS 2

void *f(void *arg) {
  	long id = (long)arg;
  	for (int i = 0; i < 100; i++) {
  		printf("Hello World din thread-ul %ld %d !\n ", id, i);

  	}
  	pthread_exit(NULL);
}

void *g(void *arg) {
  	long id = (long)arg;
  	//for (int i = 0; i < 100; i++) {
  		printf("Goodbye din thread-ul %ld !\n ", id);

  	//}
  	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
	
  	int r;
  	long id;
  	void *status;
  	//long NUM_THREADS;

 	long cores = sysconf(_SC_NPROCESSORS_CONF);
  	printf("Nr of cores: %ld\n",cores );

  	//NUM_THREADS = cores;


  	pthread_t threads[NUM_THREADS];


  	//for (id = 0; id < NUM_THREADS; id++) {
  	id = 0;
		r = pthread_create(&threads[id], NULL, f, (void *)id);

		if (r) {
	  		printf("Eroare la crearea thread-ului %ld\n", id);
	  		exit(-1);
		}
	id = 1;
		r = pthread_create(&threads[id], NULL, g, (void *)id);

		if (r) {
	  		printf("Eroare la crearea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	//}

  	for (id = 0; id < NUM_THREADS; id++) {
		r = pthread_join(threads[id], &status);

		if (r) {
	  		printf("Eroare la asteptarea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	}
 

  	pthread_exit(NULL);
}
