## Vector

### Sort
```cpp
std::sort(str.begin(), str.end())
```

### Vector append another vector
```cpp
a.append(a.end(), b.begin(), b.end())
```

### Search in a vector
```cpp
// return an iterator
std::find(v.begin(), v.end(), value)
```

### Sum of the vector
```cpp
#include <numeric>
int sum = std::accumulate(v.begin(), v.end(), 0);
```

### Find min element
```cpp
#include <algorithm> 
ForwardIterator it =  std::min_element (v.begin(), v.end() );
```
### K largest emelemts
```cpp
nth_element(v.begin(), v.begin() + k, v.end(), std::greater<int>())
The elements from v.begin() to v.begin() + k are k largest elements
```
### Remove if

```cpp
v.erase(std::remove_if( v.begin(), v.end(), [](const int& x) { 
        return x > 10; // put your condition here
    }), v.end());
```


## String

### Split string
```cpp
#include "stdafx.h"
#include <sstream>
#include <algorithm>

using namespace std;

vector<string> split(string s, char delim)
{
    vector<string> elem;
    std::stringstream ss(s);
    string item;

    while (std::getline(ss, item, delim))
    {
	elem.push_back(item);
    }

    return elem;
}
```

### String to interger
`stoi` string to int
`atoi` char* to int
	
### Convert string to lower case
```cpp
#include <string>
#include <algirhtm>
std::transform(s.begin(), s.end(), s.begin(), tolower);
```


## Queue
### Priority-Queue construct with comparator 
```cpp
template <typename T>
Struct comparator
{
	Bool operator() (T a, T b)
	{
		Return a < b;
	}
};
Std::priority_queue<int, std::vector<int>, comparator<int>> max_heap;
```

### BST in c++
We can use multiset<int> to represent a BST.

### c++ priority_queue is max-heap by default.
We can create min-heap by set the comparator as greater<int>
```cpp
#include <functional>
std::priority_queue<int, vector<int>, std::greater<int>> minHeap
```
