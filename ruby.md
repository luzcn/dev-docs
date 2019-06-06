## Define Ruby class
```ruby
class Customer
    # Instance varialbe
    # similar to java member field
    @no_customer = 0    
    
    # Class varialbe
    # similar to java static field
    # A class variable belongs to the class
    @@total_customer = 0 

    # Global varialbe
    # it is available across classes
    # similar to C++ friend
    $all_customers = 0
    
    
    # define the constructor/initialize method
    def initialize(id, name, addr)
      @cust_id = id
      @cust_name = name
      @cust_addr = addr
   end
   
   # instance method
   def get_name
      @cust_name
   end
   
   def set_name = (name)
      @cust_name=name
   end
end

# create objects
cust1 = Customer.new("1", "John", "Wisdom Apartments, Ludhiya")
cust2 = Customer.new("2", "Poul", "New Empire road, Khandala")
```

## create object and use class methods
```ruby
class Sample
    class Sample
    def hello
        puts "Hello Ruby!"
    end
end


obj = Sample.new
obj.hello
```

## Define and use class method 
```ruby
class Box
   # Initialize our class variables
   @@count = 0
   def initialize(w,h)
      # assign instance avriables
      @width, @height = w, h

      @@count += 1
   end


    # define class method, simlar to static method in java
   def self.printCount()
      puts "Box count is : #@@count"
   end
end

# create two object
box1 = Box.new(10, 20)
box2 = Box.new(30, 100)

# call class method to print box count
Box.printCount()
```

## Inheritence and override
basic sytax: `class name < superclass`
```
# define a class
class Box
   # constructor method
   def initialize(w,h)
      @width, @height = w, h
   end
   # instance method
   def getArea
      @width * @height
   end
end

# define a subclass
class BigBox < Box

   # change existing getArea method as follows
   def getArea
      @area = @width * @height
      puts "Big box area is : #@area"
   end
end

# create an object
box = BigBox.new(10, 20)

# print the area using overriden method.
box.getArea()

```

# Ruby datastructure and operator
## array
```ruby
ary = ["my name", 10, 3.14, "this is a string", "last element"]

ary.each do |i|
    puts i
end
```

## hash
```ruby
hsh = colors = { "red" => 0xf00, "green" => 0x0f0, "blue" => 0x00f }
hsh.each do |key, value|
   print key, " is ", value, "\n"
end
```

## range
```ruby
(10..15).each do |n| 
   print n, ' ' 
end
```

## while loop
```ruby
$i = 0
$num = 5

while $i < $num  do
   puts("Inside the loop i = #$i" )
   $i +=1
end
```

## for loop
``` ruby
for i in 0..5
   puts "Value of local variable is #{i}"
end

# use break
for i in 0..5
   if i > 2 then
      break
   end
   puts "Value of local variable is #{i}"
end

# use next
for i in 0..5
   if i < 2 then
      next  # similar to java continue
   end
   puts "Value of local variable is #{i}"
end
```
## `if` modifier
the basic syntax `code if condition`, equivelent ot `if condition code end` block
```
$debug = true
print "debug\n" if $debug

# this statement equals 
if $debug
    print "debug\n"
end
```

## Ruby symbols
A Ruby symbol is not a variable because it cannot be assigned a value.
Also, a Ruby symbol is not a reference to another variable nor is it a pointer to a memory location.
```ruby
# It is trivial to assign a value to a variable.
abc = "1"
=> "1"
 
# But a symbol cannot be assigned any value.
:a = "1"
# SyntaxError: A symbol cannot be assigned a value
 
# Can use a variable as a map-key (You know already)
m = {abc => "1"}
=> {"1"=>"1"}

# Can use a string as a map-key (You know already)
m = {"def" => "1"}
=> {"def"=>"1"}

# Can also use a symbol as a map-key (Most common use case)
m = {:a => "1"}
=> {:a=>"1"}
 
# Can use same symbol as key in another map
m2 = {:a => "2"}
=> {:a=>"2"}

# And it won't affect the previous map.
m
=> {:a=>"1"}
 
m2
=> {:a=>"2"}

```

## difference between `foo:` and `:foo`
:foo is a Symbol literal, just like 'foo' is a String literal and 42 is an Integer literal.

foo: is used in three places:
- as an alternative syntax for Symbol literals as the key of a **Hash literal**: `{ foo: 42 }`  the same as `{ :foo => 42 }`
- in a **parameter list** for declaring a **keyword parameter**: `def foo(bar:) end`
- in an argument list for passing a keyword argument: `foo(bar: 42)`


## ||= (Double Pipe / Or Equals) 
In Ruby, we can use `||=` passing reference to assign a variable if it is `nil`.

`a ||= b` is translated to 
```java
if (a == null) {
  a = b;
 }
```

## pass key-value parameters
```ruby
module Test
    class Client
        def get_user
            request(
                method: :get,
                expects: 200
            )
        end


        def request(options) 
            print options
        end
        
    end

    client = Client.new
    client.get_user
    
    # => {:method=>:get, :expects=>200}
end
```

# Ruby yield and blocks
the `yield` keyword means we will define/add more actions for this data object later.
```ruby
class Array

  def iterate!
    self.each_with_index do |n, i|
      self[i] = yield(n)
    end
  end
end

arr = [1,2,3,4]

arr.iterate! do |n|
  n**2
end

puts arr.inspect
```
