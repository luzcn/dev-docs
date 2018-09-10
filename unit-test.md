### Mock local variable

Use `PowerMockito` to mock the `new` construction


```
@PrepareForTest(Server.class)

PowerMockito.whenNew(EventsProducer.class).withNoArguments().thenReturn(mockProducer);
PowerMockito.whenNew(Logger.class).withNoArguments().thenReturn(mockLogger);
```
