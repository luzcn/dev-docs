## Resources
- [awesome-go](https://github.com/avelino/awesome-go) 


## Slides
[topic1: Syntax](https://talks.godoc.org/github.com/davewadestein/gott/targeted-topics1.slide#1)

[topic2: Control/Data Structures](https://talks.godoc.org/github.com/davewadestein/gott/targeted-topics2.slide#1)

[topic3: Files + Concurrency + Documentation](https://talks.godoc.org/github.com/davewadestein/gott/targeted-topics3.slide#1)

## Go Examples

### Decode/Encode JSON 
1. Decode a JSON string to arbitrary objects and arrays

Sometimes, we need to process data whose structure or property names that are not certain of, we cannot use structs to unmarshal the data. Instead we can use maps. 

The encoding/json package uses

  - map[string]interface{} to store arbitrary JSON objects, and
[]interface{} to store arbitrary JSON arrays.
- It will unmarshal any valid JSON data into a plain interface{} value.

```go
birdJson := `{"birds":{"pigeon":"likes to perch on rocks","eagle":"bird of prey"},"animals":"none"}`
var result map[string]interface{}
json.Unmarshal([]byte(birdJson), &result)

// The object stored in the "birds" key is also stored as 
// a map[string]interface{} type, and its type is asserted from
// the interface{} type
birds := result["birds"].(map[string]interface{})

for key, value := range birds {
  // Each value is an interface{} type, that is type asserted as a string
  fmt.Println(key, value.(string))
}
```



### Sample code using kingpin for CLI interface
```go
package main

import (
	"fmt"
	"gopkg.in/alecthomas/kingpin.v2"
	"os"
)

var app = kingpin.New("go-db", "CLI for database management")
var version = app.Version("0.0.1")

var (
	migrateCmd = app.Command("migrate", "Run database migrations")
)

func main() {
	switch kingpin.MustParse(app.Parse(os.Args[1:])) {
	case migrateCmd.FullCommand():
		fmt.Println("Migrate")
	default:
		fmt.Println(`unknown command, please use "go-db --help"`)
	}
}
```

### go routing and channel
```go
package main

import "fmt"

func calcSqure(number int, res chan int) {

	sum := 0
	for number != 0 {
		digit := number % 10
		sum += digit * digit
		number /= 10
	}

	res <- sum
}

func calcCube(number int, res chan int) {
	sum := 0
	for number != 0 {
		digit := number % 10
		sum += digit * digit * digit
		number /= 10
	}

	// write to channel
	res <- sum
}

func main() {

	var squreChan = make(chan int)
	var cubeChan = make(chan int)
	var num = 589

	go calcSqure(num, squreChan)
	go calcCube(num, cubeChan)

	// read from channel
	res1, res2 := <-squreChan, <-cubeChan
	fmt.Println(res1 + res2)
}
```

### go routine, channels and select
```go
// a simple sort go routine example
func main() {
	x := []int{2, 4543, 6, 1, 45, 6}
	ci := make(chan int)

	go func() {
		sort.Ints(x)
		ci <- 1
	}()

	<-ci

	fmt.Println(x)
}


// a simple go routing and select example

func main() {
	value := make(chan int)
	quit := make(chan int)

	go fib(value, quit)

	for i := 0; i < 10; i++ {
		// print data from value channel
		fmt.Println(<-value)
	}
	// send value to quit channel
	quit <- 0
}

func fib(c, quit chan int) {
	x, y := 0, 1
	for {
		// The select statement lets a goroutine wait on multiple communication operations.
		select {
		// A select blocks until one of its cases can run, then it executes that case. 
		// It chooses one at random if multiple are ready.
		case c <- x:
			x, y = y, x+y
		case <-quit:
			// when "quit" has value, exit this routine
			fmt.Println("quit")
			return
		}
	}
}
```

## Basic 


### Read files
```go
package main

import (
	"os"
)

func main() {
	buf := make([]byte, 1024)
	f, e := os.Open("./README.md")
	if e != nil {
		panic(e)
	}

	for {
		n, e := f.Read(buf)
		if n == 0 {
			break
		}
		if e != nil {
			panic(e)
		}

		os.Stdout.Write(buf[:n])
	}

	f.Close()
}

```

### Use scanner read file line by line
```go
import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	file, err := os.Open("./README.md")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	// read line by line
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		fmt.Fprintln(os.Stderr, "reading standard input: ", err)
	}
}
```

### use interface
`interface` is a named collection of method signatures. 
To implement an interface in Go, we just need to implement all the methods in the interface

```go
package main

import (
	"fmt"
	"math"
)

// A basic interface for geometric shapes
type geometry interface {
	area() float64
	perimeter() float64
}

type rectangle struct {
	height float64
	width  float64
}

type circle struct {
	radius float64
}

// implement the "gemoetry" interface ("area" and "perimeter" methods) for type rectangle
func (rec rectangle) area() float64 {
	return rec.width * rec.height
}
func (rec rectangle) perimeter() float64 {
	return 2*rec.width + 2*rec.height
}

// implement the "gemoetry" interface ("area" and "perimeter" methods) for type circle
func (c circle) area() float64 {

	return math.Pi * c.radius * c.radius
}

func (c circle) perimeter() float64 {
	return 2 * math.Pi * c.radius
}

func measure(g geometry) {
	fmt.Println(g)

	fmt.Printf("The geometry area is %f\n", g.area())
	fmt.Printf("The geometry perimeter is %f\n", g.perimeter())
}

func main() {
	measure(rectangle{2,4})
}
```

### create a slice of slice 
```go
// similar to java List<List<Integer>> x = new ArrayList<>()
x := [][]int{}

x = append(x, []int{1, 24, 4})
x = append(x, []int{1, 2})
```

### reverse a slice
```go
func reverse(nums []int) {
	for i, j := 0, len(nums)-1; i < j; i, j = i+1, j-1 {
		nums[i], nums[j] = nums[j], nums[i]
	}
}
```

### rotate the slice to left n elements
```go
func main() {
	nums := []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}

	rotateLeft(3, nums)
	fmt.Println(nums)
}

// roatet the given nums slice left n elements
// n is guaranteed to be valid
func rotateLeft(n int, nums []int) {
	if n > len(nums) {
		return
	}

	// reverse the first n elements
	// e.g. n is 3 => 3,2,1,4,5,6,7,8,9,10,11,12
	reverse(nums[:n])

	// reverse the rest elements
	reverse(nums[n:])

	// reverse entire slice
	reverse(nums)
}

func reverse(nums []int) {
	for i, j := 0, len(nums)-1; i < j; i, j = i+1, j-1 {
		nums[i], nums[j] = nums[j], nums[i]
	}
}
```

### `for` loop
```go
// while loop
for i < 10 {
    i++
}

// for with condition
for i:=1; i < 10; i++ {
    fmt.Println(i)
}

// for with range
nums := []int{1,2,3,4,12}
for i,n := range nums {
    fmt.Println(n)
}
```

### use hashmap
```go
// declare a hashmap object
var mp map[string]int

// define a hashmap
mp := make(map[string]int)

// check if an element in the map
v,found := mp["key"]

// directly use in if condition
if _,found := mp["key"]; found {
    // do something
}

```

### use `switch`
```go
func test() map[string]string {
	return map[string]string{}
}

func main() {
	var t interface{}

	t = test()

	switch t.(type) {
	case bool:
		fmt.Printf("the boolean type is %T", t)

	default:
		fmt.Printf("the interface type is %T", t)
	}
}
```

#### string to int, int to string
```go
// string to integer
s, _:= strconv.Atoi("123")
print(s)


// integer to string
s := strconv.Itoa(123)
print(s)

// float64 to string
s := strconv.FormatFloat(123.12301, 'f', 3, 64)
print(s)

// string to float64
f, err := strconv.ParseFloat("3.1415", 64)
print(f)

// convert binary to int64
i, err := strconv.ParseInt("-42", 10, 64)
print(i)
```

#### Returns multiple values
```go
package example
import (
    "math"
    "strconv"
)

func CircleInfo(radius float64) (string, string) {
	area := math.Pi * radius * radius
	circumference := math.Pi * radius * 2

	// convert to string type
	areaStr := strconv.FormatFloat(area, 'f', 1, 64)
	circumferenceStr := strconv.FormatFloat(circumference, 'f', 1, 64)

	return areaStr, circumferenceStr
}

func main() {
    CircleInfo(12.3)
}
```

### Printf example
```go
package example
import "fmt"

func printf_exercise() {

	//%f = print a float value
	//%v = print the value in a default format
	//%T = print the type of the variable
	//%t = print a Boolean value as true or false
	var f float64 = 38730204.3832
	fmt.Printf("%v\n", f)
	fmt.Printf("%f\n", f)
	fmt.Printf("%.2f\n", f)

	var i int = 13
	fmt.Printf("|%5d|\n", i)
	fmt.Printf("|%-5d|\n", i)

	var b bool = false
	fmt.Printf("%t\n", b)
	fmt.Printf("%T %T %T\n", f, i, b)

	// read from stdin
	var num int
	_, err := fmt.Scan(&num)
	if err != nil {
		fmt.Print(err)
	}
	fmt.Print(num)
}

```
