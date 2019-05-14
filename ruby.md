# Define Ruby class
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
# `if` modifier
the basic syntax `code if condition`, equivelent ot `if condition code end` block
```
$debug = true
print "debug\n" if $debug

# this statement equals 
if $debug
    print "debug\n"
end
```
