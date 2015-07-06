#include<stdio.h>


int main(void)
{
    rek();
    return 0;
}

void rek(){
   int arr[1000000];
   arr[100]=1;
   rek();
}
