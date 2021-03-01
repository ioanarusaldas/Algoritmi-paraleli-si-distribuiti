#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>

#define MASTER 0

int main (int argc, char *argv[])
{
    int procs, rank;
    int recv_num;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    int value = rank;
/*    if (rank == MASTER) {
        MPI_Send(&value, 1, MPI_INT, 1 , 1, MPI_COMM_WORLD);
    }*/

    for (int i = 1; i < procs; i *= 2) {
       // printf("PAS %d\n",i);
        // TODO
        if (rank + i < procs) {
            //trimite la procesul cu rank-ul [rank + pas]
            MPI_Send(&value, 1, MPI_INT, (rank + i) , 1, MPI_COMM_WORLD);
            // printf("Process [%d] trimite = %d\n", rank,value);
        
        }
        if (rank - i >= 0) {
            //primeste de la procesul cu rank-ul [rank - pas]
          
            MPI_Status status;
            MPI_Recv(&recv_num, 1, MPI_INT, (rank - i ), 1, MPI_COMM_WORLD, &status);
            //aplicatia operatia
            // printf("Process [%d] primeste = %d\n", rank,recv_num);

            value += recv_num;
            //printf("Process [%d] has result = recv\n", rank);
        }


    }

    printf("Process [%d] has result = %d\n", rank, value);

    MPI_Finalize();

}

