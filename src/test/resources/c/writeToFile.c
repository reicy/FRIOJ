#include<stdio.h>


int main(void)
{
    FILE *out;
    out=fopen("sth.txt", "w");
    fprintf(out,"sth");
    close(out);	 
    
    return 0;
}
