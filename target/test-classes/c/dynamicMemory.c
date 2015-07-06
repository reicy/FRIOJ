#include<stdio.h>


int main(void)
{
    int *ptr[1000000];




			for (int m = 0; m < 1000000; m++) {

					ptr[m]=(int*)malloc(10000*sizeof(int));
					ptr[m][1000] = 1;
					printf(" ");

			}


	

    return 0;
}
