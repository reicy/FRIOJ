#include<iostream>

int a1[100000000];
int a2[100000000];
int a3[100000000];
int a4[100000000];
int a5[100000000];
int a6[10000];

int main(void)
{
    int *ptr[100000000];


	for (int k = 0; k < 10000000; k++) {
		for (int l = 0; l < 10000000; l++) {
			for (int m = 0; m < 10000000; m++) {
				for (int n = 0; n < 1000; n++) {
					printf("a");

				}
			}

		}
	}

    return 0;
}
