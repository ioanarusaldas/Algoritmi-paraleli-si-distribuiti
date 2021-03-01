#include<mpi.h>
#include<stdio.h>
#include<stdlib.h>
#include<math.h>

#define N 16
#define MASTER 0

void compareVectors(int * a, int * b) {
	// DO NOT MODIFY
	int i;
	for(i = 0; i < N; i++) {
		if(a[i]!=b[i]) {
			printf("Sorted incorrectly\n");
			return;
		}
	}
	printf("Sorted correctly\n");
}

void displayVector(int * v) {
	// DO NOT MODIFY
	int i;
	int displayWidth = 2 + log10(v[N-1]);
	for(i = 0; i < N; i++) {
		printf("%d ",  v[i]);
	}
	printf("\n");
}

int cmp(const void *a, const void *b) {
	// DO NOT MODIFY
	int A = *(int*)a;
	int B = *(int*)b;
	return A-B;
}

int min(int a, int b) {
	// DO NOT MODIFY
	if (a < b)
		return a;
	return b;
}
 
int main(int argc, char * argv[]) {
	int rank, i, j;
	int nProcesses;
	MPI_Init(&argc, &argv);
	int pos[N];
	int sorted = 0;
	int *v = (int*)malloc(sizeof(int)*N);
	int *vQSort = (int*)malloc(sizeof(int)*N);
	int *vAux = (int*)malloc(sizeof(int)*N);

	for (i = 0; i < N; i++)
		pos[i] = 0;

	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &nProcesses);
	printf("Hello from %i/%i\n", rank, nProcesses);

    if (rank == MASTER) {
        // generate random vector
   		v [0] = 6;
   		v [1] = 13;
   		v[2] = 8;
   		v[3] = 10;
   		v[4] = 5;
   		v[5] = 12;
   		v[6] = 7;
   		v[7] = 3;
   		v[8] = 9;
   		v[9]= 0;
   		v[10] = 2;
   		v[11] = 1;
   		v[12] = 14;
   		v [13]= 15;
   		v[14] =11;
   		v[15] = 4;
    

    // send the vector to all processes
    	for (int i = 0; i < nProcesses - 1; i++)
    		MPI_Send(v, N, MPI_INT,i + 1, 1, MPI_COMM_WORLD);
	}


	if(rank == 0) {
		// DO NOT MODIFY
		displayVector(v);

		// make copy to check it against qsort
		// DO NOT MODIFY
		for(int i = 0; i < N; i++)
			vQSort[i] = v[i];
		qsort(vQSort, N, sizeof(int), cmp);

		
		
        // recv the new pozitions
        int dim = N / (nProcesses - 1); 

        for(int i = 0; i < nProcesses - 1; i++){
        	 int start, end;
       		start = i* dim;
			end = min((i +  1) * dim, N);
        	for (int k = start; k < end;  k++) {
        		MPI_Status status;
        		MPI_Recv(&pos[k], 1 ,MPI_INT,i + 1, 1, MPI_COMM_WORLD, &status);
        	}
        }
        // sort the vector v
        for(int i = 0; i < N; i++){
        	//printf("%d ",pos[i] );
        	vAux[pos[i]] = v[i];
        }
        //printf("\n");
		displayVector(vAux);
		compareVectors(vAux, vQSort);
	} else {
		
        // compute the positions
        int dim = N / (nProcesses - 1); 
        int start, end;
       	start = (rank - 1) * dim;
		end = min(rank * dim, N);
		printf("%d %d\n",start,end );
		MPI_Status status;
        MPI_Recv(v, N, MPI_INT,0, 1, MPI_COMM_WORLD, &status);
        for (int i = start; i < end ; i ++) {
        	for (int j = 0; j < N; j ++) {
        		if (v[j] < v [i]) {
        			pos[i]++;
        		}
        	}
        }
        // send the new positions to process MASTER
        for (int k = start; k < end;  k++) {
        	//printf("%d ",pos[k] );
        	MPI_Send(&pos[k], 1, MPI_INT,0, 1, MPI_COMM_WORLD);
        }
        //printf("\n");
	}

	MPI_Finalize();
	return 0;
}
