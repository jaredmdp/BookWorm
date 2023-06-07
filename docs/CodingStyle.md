[<Back](./../README.md)

# Coding Style

## Naming

All variable names must be be camel case.
```java
bookPersistenceStub
```

All View classes should be the description followed by an underscore and ViewHandler
```java
Home_ViewHandler
```

All Interface classes shall be prefixed with 'I' starting with Iteration 2.
```java
IDatabase
```

## Format
All opening curly brackets shall sit *beside* the line, not under, followed by a newline.
```java
public boolean doMethod() {
}
```

All indenting is done with tabs.

Matching braces always line up vertically in the same column as their construct.
```java
if(condition) {
    do thing
}
```

All classes should be setup as follows:
```java
class Class {
    //fields
    
    //constructors
    
    //methods
    
    @override
    //methods
}
```

## Documentation
Avoid smelly code. Functions should be de-coupled and cohesive. Variable names should be self-describing its functionality. 

Comment only when necessary.