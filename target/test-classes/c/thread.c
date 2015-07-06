#include<stdio.h>
#include<string.h>
#include<pthread.h>
#include<stdlib.h>
#include<unistd.h>
#include<errno.h>

pthread_t tid[1];

void* run(void *arg)
{
   printf("thread spawned\n");
   return NULL;
}

int main(void)
{
    pthread_create(&(tid[0]), NULL, &run, NULL);
     
    sleep(2);
    return 0;
}
