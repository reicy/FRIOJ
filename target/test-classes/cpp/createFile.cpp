#include<iostream>
#include<fstream>

using namespace std;


int main(void)
{
    ofstream out;
    out.open("sth.txt"); 
    out.close();   

    return 0;
}
