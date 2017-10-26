### @Value
`@Value` is the immutable variant of `@Data`; 
**all fields are made private and final by default**, and **setters are not** generated.

Like `@Data`, useful `toString()`, `equals()` and `hashCode()` methods are also generated, each field gets a getter method, 
and a constructor that covers every argument (except final fields that are initialized in the field declaration) is also generated.

### @Builder
`@Builder` lets you automatically produce the code required to have your class be instantiable with code such as:
`Person.builder().name("Adam Savage").city("San Francisco").job("Mythbusters").job("Unchained Reaction").build();`
