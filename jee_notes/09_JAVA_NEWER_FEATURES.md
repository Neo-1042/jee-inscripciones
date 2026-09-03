# LTS Java's Newer Features

- Java 8 ---> Lambda expressions and streams.
- Java 9 ---> Modules
- Java 11 ---> Reduced code verbosity (Did it worsen code readability and maintainability?):
    - Switch expressions
    - Text blocks
    - Records (reduces boilerplate for data objects)
    - Pattern matching
- Java 21 ---> Project Loom (virtual threads for high-performance concurrency). Cloud & Container ready.
GraalVM = Enables efficient native image compilation
for lightweight applications.
- Java 25 ---> 

### Records

```java
// No need to write getters, setters, hash() or equals()
// Java 11
record Person (String name, int age) {}
```

### Switch Expressions

```java
// Java 11
String result = switch (day) {
    case MONDAY, FRIDAY -> "Weekend is near!";
    default -> "Regular day";
};
```

### Virtual Threads

```java
// Java 21
Thread.startVirtualThread(
    () -> System.out.println("Hello, from a virtual thread")
);
```

### JShell (Java 9)

