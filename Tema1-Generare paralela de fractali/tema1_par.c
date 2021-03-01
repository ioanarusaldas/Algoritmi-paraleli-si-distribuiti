/*
 * APD - Tema 1
 * Octombrie 2020
 */
//Savu Ioana Rusalda 335CB

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <pthread.h>

char *in_filename_julia;
char *in_filename_mandelbrot;
char *out_filename_julia;
char *out_filename_mandelbrot;
pthread_barrier_t barrier;
int Number_of_threads;

// structura pentru un numar complex
typedef struct _complex {
	double a;
	double b;
} complex;

// structura pentru parametrii unei rulari
typedef struct _params {
	int is_julia, iterations;
	double x_min, x_max, y_min, y_max, resolution;
	complex c_julia;
} params;

typedef struct _julia_params {
	int id;
	params par;
	int width, height;
	int **result;
} julia_params;


params par;
int width, height;
int **result;

// citeste argumentele programului
void get_args(int argc, char **argv)
{
	if (argc < 5) {
		printf("Numar insuficient de parametri:\n\t"
				"./tema1 fisier_intrare_julia fisier_iesire_julia "
				"fisier_intrare_mandelbrot fisier_iesire_mandelbrot\n");
		exit(1);
	}

	in_filename_julia = argv[1];
	out_filename_julia = argv[2];
	in_filename_mandelbrot = argv[3];
	out_filename_mandelbrot = argv[4];
}

// citeste fisierul de intrare
void read_input_file(char *in_filename, params* par)
{
	FILE *file = fopen(in_filename, "r");
	if (file == NULL) {
		printf("Eroare la deschiderea fisierului de intrare!\n");
		exit(1);
	}

	fscanf(file, "%d", &par->is_julia);
	fscanf(file, "%lf %lf %lf %lf",
			&par->x_min, &par->x_max, &par->y_min, &par->y_max);
	fscanf(file, "%lf", &par->resolution);
	fscanf(file, "%d", &par->iterations);

	if (par->is_julia) {
		fscanf(file, "%lf %lf", &par->c_julia.a, &par->c_julia.b);
	}

	fclose(file);
}

// scrie rezultatul in fisierul de iesire
void write_output_file(char *out_filename, int **result, int width, int height)
{
	int i, j;

	FILE *file = fopen(out_filename, "w");
	if (file == NULL) {
		printf("Eroare la deschiderea fisierului de iesire!\n");
		return;
	}

	fprintf(file, "P2\n%d %d\n255\n", width, height);
	for (i = 0; i < height; i++) {
		for (j = 0; j < width; j++) {
			fprintf(file, "%d ", result[i][j]);
		}
		fprintf(file, "\n");
	}

	fclose(file);
}

// aloca memorie pentru rezultat
int **allocate_memory(int width, int height)
{
	int **result;
	int i;

	result = malloc(height * sizeof(int*));
	if (result == NULL) {
		printf("Eroare la malloc!\n");
		exit(1);
	}

	for (i = 0; i < height; i++) {
		result[i] = malloc(width * sizeof(int));
		if (result[i] == NULL) {
			printf("Eroare la malloc!\n");
			exit(1);
		}
	}

	return result;
}

// elibereaza memoria alocata
void free_memory(int **result, int height)
{
	int i;

	for (i = 0; i < height; i++) {
		free(result[i]);
	}
	free(result);
}

//calculeaza minimul a 2 numere
int min(int a , int b) {
	if (a > b)
		return b;
	return a;
}

// ruleaza algoritmul Julia
void run_julia(int thread_id){
 	int w, h, i;

 	//calculare interval pentru fiecare thread
	int start_w = thread_id * (double)width/ Number_of_threads;
	int end_w = min((thread_id + 1) * (double)width/ Number_of_threads,width);

	// int start_h = thread_id * (double)height/ P;
	// int end_h = min((thread_id + 1) * (double)height/ P,height);

	for (w = start_w; w < end_w; w++) {
		for (h = 0; h < height; h++) {
			int step = 0;
			complex z = { .a = w * par.resolution + par.x_min,
							.b = h * par.resolution + par.y_min };

			while (sqrt(pow(z.a, 2.0) + pow(z.b, 2.0)) < 2.0 && step < par.iterations) {
				complex z_aux = { .a = z.a, .b = z.b };

				z.a = pow(z_aux.a, 2) - pow(z_aux.b, 2) + par.c_julia.a;
				z.b = 2 * z_aux.a * z_aux.b + par.c_julia.b;

				step++;
			}

			result[h][w] = step % 256;
		}
	}

	//bariera pentru a ne asigura ca toate threadurile au calculat matricea
	pthread_barrier_wait(&barrier);

	// transforma rezultatul din coordonate matematice in coordonate ecran
	h = height / 2;
	//calculare interval pentru fiecare thread
	int start_h = thread_id * (double)h/ Number_of_threads;
	int end_h = min((thread_id + 1) * (double)h/ Number_of_threads,h);
	
	for (i = start_h; i < end_h; i++) {
		int *aux = result[i];
		result[i] = result[height - i - 1];
		result[height - i - 1] = aux;
	}
}

// ruleaza algoritmul Mandelbrot
void run_mandelbrot(int thread_id)
{
	int w, h, i;

	//calculare interval pentru fiecare thread
	int start_h1 = thread_id * (double)height/ Number_of_threads;
	int end_h1 = min((thread_id + 1) * (double)height/ Number_of_threads,height);

	for (w = 0; w < width; w++) {
		for (h = start_h1; h < end_h1; h++) {
			complex c = { .a = w * par.resolution + par.x_min,
							.b = h * par.resolution + par.y_min };
			complex z = { .a = 0, .b = 0 };
			int step = 0;

			while (sqrt(pow(z.a, 2.0) + pow(z.b, 2.0)) < 2.0 && step < par.iterations) {
				complex z_aux = { .a = z.a, .b = z.b };

				z.a = pow(z_aux.a, 2.0) - pow(z_aux.b, 2.0) + c.a;
				z.b = 2.0 * z_aux.a * z_aux.b + c.b;

				step++;
			}

			result[h][w] = step % 256;
		}
	}

	//bariera pentru a ne asigura ca toate threadurile au calculat matricea
	pthread_barrier_wait(&barrier);

	//calculare interval pentru fiecare threa
	h = height / 2;
	int start_h = thread_id * (double)h/ Number_of_threads;
	int end_h = min((thread_id + 1) * (double)h/ Number_of_threads,h);

	// transforma rezultatul din coordonate matematice in coordonate ecran
	for (i = start_h; i < end_h; i++) {
		int *aux = result[i];
		result[i] = result[height - i - 1];
		result[height - i - 1] = aux;
	}
}



void *thread_function(void *arg) {
	//extragere id thread
	int thread_id = *(int *)arg;

	//alegere thread 0 sa faca operatiile de citire si alocare
	if (thread_id == 0) {
		//citire parametrii de intare
		read_input_file(in_filename_julia, &par);

		width = (par.x_max - par.x_min) / par.resolution;
		height = (par.y_max - par.y_min) / par.resolution;

		//se aloca tabloul cu rezultatul
		result = allocate_memory(width, height);
	}

	//bariera pentru a ne asigura ca thred-ul 0 a alocat tabloul
	pthread_barrier_wait(&barrier);
	//rulare algoritm julia
	run_julia(thread_id);
	//bariera pentru a ne asigura ca toate thread-urile au terminat algoritmul
	pthread_barrier_wait(&barrier);

	//alegere thread 0 sa faca operatiile de scriere,dezalocare,citire si 
	//alocare
	if (thread_id == 0) {
		//scriere fisier
		write_output_file(out_filename_julia, result, width, height);
		//eliberare memorie
		free_memory(result, height);

		//citire parametrii intrare
		read_input_file(in_filename_mandelbrot, &par);

		width = (par.x_max - par.x_min) / par.resolution;
		height = (par.y_max - par.y_min) / par.resolution;
		//se aloca tabloul cu rezultatul
		result = allocate_memory(width, height);
	}

	//bariera pentru a ne asigura ca thred-ul 0 a terminat operatiile anterioare
	pthread_barrier_wait(&barrier);
	//rulare algoritm mandelbrot
	run_mandelbrot(thread_id);
	//bariera pentru a ne asigura ca toate thread-urile au terminat algoritmul
	pthread_barrier_wait(&barrier);

	//alegere thread 0 sa faca operatiile de scriere,dezalocare
	if (thread_id == 0) {
		write_output_file(out_filename_mandelbrot, result, width, height);
		free_memory(result, height);
	}

	pthread_exit(NULL);

}

int main(int argc, char *argv[])
{

	// se citesc argumentele programului
	get_args(argc, argv);

	//extragere numar de threaduri
	Number_of_threads = atoi(argv[5]);
	//declarare vectori threaduri
	pthread_t tid[Number_of_threads];
	int thread_id[Number_of_threads];

	//initializare bariera
	pthread_barrier_init(&barrier, NULL, Number_of_threads);
	int i;

	//creare threaduri
	for (i = 0; i < Number_of_threads; i++) {
		thread_id[i] = i;
		pthread_create(&tid[i], NULL, thread_function, &thread_id[i]);
	}

	for (i = 0; i < Number_of_threads; i++) {
		pthread_join(tid[i], NULL);
	}

	//dezalocare bariera
	pthread_barrier_destroy(&barrier);
	return 0;
}
