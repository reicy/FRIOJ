#include <iostream>       
#include <thread>        
 
void run() 
{
  std::cout << "thread spawned\n";
}

int main() 
{
  std::thread first (run);     

  first.join();               

  return 0;

}
