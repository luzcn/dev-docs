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

# create object and use class methods
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
