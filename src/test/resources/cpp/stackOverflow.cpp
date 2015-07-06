#include<iostream>


void rek(){
   int arr[1000000];
   int arr1[1000000];
   int arr2[1000000];
   int arr3[1000000];
   arr[100]=1;
   rek();
}

int main(void)
{
    rek();
    return 0;
}

