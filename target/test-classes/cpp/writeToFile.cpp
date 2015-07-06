#include<iostream>
#include<fstream>

using namespace std;


int main(void)
{
    ofstream out;
    out.open("sth.txt"); 
    out << "sth\n";
    out.close();   

    return 0;
}
