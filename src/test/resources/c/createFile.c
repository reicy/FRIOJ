#include<stdio.h>


int main(void)
{
    FILE *out;
    out=fopen("sth.txt", "w");
    close(out);
    return 0;
}
