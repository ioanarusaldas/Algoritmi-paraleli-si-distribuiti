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

    for (int i = 2; i <= procs; i *= 2) {
        // TODO
        if (rank % i == 0) {
        //primeste la procesul cu rank-ul [rank + (pas / 2)]
            MPI_Status status;
            MPI_Recv(&recv_num, 1, MPI_INT, (rank + (i / 2)), 1, MPI_COMM_WORLD, &status);
            value += recv_num;
        }
        //aplica operatia

        else if (rank % (i / 2) == 0) {
            MPI_Send(&value, 1, MPI_INT, (rank - (i / 2)) , 1, MPI_COMM_WORLD);
        }
       // trimite la procesul cu rank-ul [rank - (pas / 2)]

    }

    if (rank == MASTER) {
        printf("Result = %d\n", value);
    }

    MPI_Finalize();

}

