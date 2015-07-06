#include<stdio.h>


int main(void)
{
    int** arr = new int*[100000000];

    for (int k = 0; k < 10000000; k++) {
	arr[k] = new int[100000];
    }

	
    return 0;
}
