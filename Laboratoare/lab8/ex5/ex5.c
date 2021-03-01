#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>

#define ROOT 0

int main (int argc, char *argv[])
{
    int  numtasks, rank;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    MPI_Comm_rank(MPI_COMM_WORLD,&rank);

    // Checks the number of processes allowed.
    if (numtasks != 2) {
        printf("Wrong number of processes. Only 2 allowed!\n");
        MPI_Finalize();
        return 0;
    }

    // How many numbers will be sent.
    int send_numbers = 10;
    int randN, randT;


    if (rank == 0) {

        // Generate the random numbers.
        // Generate the random tags.
        // Sends the numbers with the tags to the second process.
        for (int i = 0; i < send_numbers; i++) {
            randN = rand() % 50;
            randT = rand() % 10;
            MPI_Send(&randN, 1, MPI_INT, 1, randT, MPI_COMM_WORLD);
            printf("Process with rank [%d] sent the number %d with tag %d.\n", rank, randN, randT);
        }
    } else {

        // Receives the information from the first process.
        // Prints the numbers with their corresponding tags
        MPI_Status status;
        MPI_Recv(&randN, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
        printf("Process with rank [%d], received %d with tag %d.\n",
                rank, randN, status.MPI_TAG);
    }

    MPI_Finalize();

}

