/*TODO:
	- fill in functions
	- more consistensy with variable names
	- make sure each constraint is met
	- test test test
*/

#include <stdlib.h>
#include <stdio.h>

/*Add documentation*/
int get_num_of_sets();
int get_num_in_set();
void get_set_values(float *data_array, int size);

int get_opt();
int get_set();

void find_min( float *data, int size );
void find_max( float *data, int size );
void find_sum( float *data, int size );
void find_avg( float *data, int size );
void sort_dat( float *data, int size );

void free_space(float **ptr, int size);

int main(){
	int num_sets;		/*Number of data sets*/
	int i;			/*Counter variable*/
	int set_choice;		/*The user's choice of set*/
	int opt_choice;		/*The user's choice of option*/
	int *set_sizes;		/*Stores the size of each set*/
	float **data_sets;	/*pointer to a pointer to a float*/
	
	/*Getting number of sets to be entered*/
	num_sets = get_num_of_sets();
	
	/*Allocating space for array of pointers to float*/
	data_sets = malloc(num_sets*sizeof(float*));
	
	/*Allocaring space for array if ints*/
	set_sizes = malloc(num_sets*sizeof(int));
	
	/*Filling data sets*/
	for(i = 1; i < num_sets + 1; i++){
		int num_in_set;
		float *set;
		
		/*Allocating space for data*/
		num_in_set = get_num_in_set();
		set = malloc(num_in_set*sizeof(float));
		
		/*Storing size of array*/
		set_sizes[i-1] = num_in_set;
		
		/*Filling array with data*/
		get_set_values(set, num_in_set);
	}
	
	/*Setting default data set to data set 1*/
	set_choice = 1;
	
	/*Looping until user decides to exit*/
	while((opt_choice = get_opt()) != 7){
		switch( opt_choice ){
			case 1: /*minimum*/
				find_min(set, num);
			break;
			case 2: /*maximum*/
				find_max();
			break;
			case 3: /*sum*/
				find_sum()
			break;
			case 4: /*average*/
				find_avg();
			break;
			case 5: /*sort*/
				sort_dat();
			break;
			case 6: /*select set*/
				set_choice = get_set();
			break;
			default:
			/*This shouldn't happen*/
			break;
		}
	}
	
	/*Freeing up allocated space*/
	free_space(data_sets, num_sets);
}

int get_num_of_sets(){
	int num_sets; /*Number of data sets*/
	
	/*Prompt user for number of data sets*/
	printf("Please enter the number of data sets: ");
	
	/*Reading in the number of data sets*/
	scanf("%d",&num_sets);
	
	return(num_sets);
}

int get_num_in_set(){
	return(0);
}

void get_set_values(float *data_array, int size){
	int i;
	
	for( i = 1; i < size + 1; i++){
		int num_floats; /*Number of values in current data set*/
		int j; /*Counter variable*/
		printf("Please enter the number of values in data set #%i, followed by the values: ", i);
		scanf("%d",&num_floats);
		
		/*Reading in the values*/
		for( j = 0; j < num_floats; j++){
			scanf("%f",&data_array[i]);
		}
	}
}

int get_opt(){
	return(0);
}

int get_set(){
	return(0);
}

void free_space(){
}
