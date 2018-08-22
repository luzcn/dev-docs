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
