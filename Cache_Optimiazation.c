#include <stdio.h>
#include "matrix.h"

/*
(Base Function Given)

void get_every_fifth(const matrix_t *matrix, long results[RESULTS_LEN]) {
    for (int i = 0; i < matrix->rows; i++) {
        for (int j = 0; j < matrix->cols; j++) {
            int q = j % RESULTS_LEN;
            results[q] += MGET(matrix, i, j);
        }
    }
}
*/


//Cache Optimized Function
void get_every_fifth(const matrix_t *matrix, long results[RESULTS_LEN]) {
    int rows = matrix->rows;
    int cols = matrix->cols;
    int temp = cols % 5;
    int colBy5 = cols - temp;
    int *data = matrix->data;
    long sum1 = 0;
    long sum2 = 0;
    long sum3 = 0;
    long sum4 = 0;
    long sum5 = 0;
    for (int i = 0; i < rows; i++) {
    	int j = 0;
    	int q = 0;
    	int icols = i * cols;
        for (; j < colBy5; j+= 5) {
            sum1 += data[icols + (j)];
            sum2 += data[icols + (j) + 1];
            sum3 += data[icols + (j) + 2];
            sum4 += data[icols + (j) + 3];
            sum5 += data[icols + (j) + 4];   
        }
        for(; j < cols; j++){ //cleans up elemnts left over
        	results[q] += data[icols + (j)];
        	q++;
        }
    }   
    results[0] += sum1;
    results[1] += sum2;
    results[2] += sum3;
    results[3] += sum4;
    results[4] += sum5;
}
/*
(Base Function Given)

long get_every(const matrix_t *matrix) {
    long sum = 0;
    for (int i = 0; i < matrix->rows; i++) {
        for (int j = 0; j < matrix->cols; j++) {
            sum += MGET(matrix, i, j);
        }
    }
    return sum;
}
*/

//Cache Optimized Function
long get_every(const matrix_t *matrix) {
    long sum1 = 0;
    long sum2 = 0;
    long sum3 = 0;
    long sum4 = 0;
    long sum5 = 0;
    int rows = matrix->rows;
    int cols = matrix->cols;
    int *data = matrix->data;
    for (int i = 0; i < rows; i++) {
    	int j = 0;
    	int icols = i * cols;
        for (; j < (cols - 5); j += 5) {
            sum1 += data[icols + (j)];
            sum2 += data[icols + (j) + 1];
            sum3 += data[icols + (j) + 2];
            sum4 += data[icols + (j) + 3];
            sum5 += data[icols + (j) + 4];
        }
 
        for(; j < cols; j++){//cleans up elemnts left over
        	sum1 += data[icols + (j)];
        }
    }
    sum1 = sum1 + sum2 + sum3 + sum4 + sum5;
    return sum1;
}
