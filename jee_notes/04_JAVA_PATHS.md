# `java.nio.file.Paths`

The `Path` class in the `java.nio.file` package represents
a file or directory path in a file system, providing methods
to manipulate and access file system paths efficiently.

## Java NIO = New Input/Ouput (From Java 7)

Provides a more flexible and powerful file I/O API compared to
`java.io.File`.

A `Path` object represents a hierarchical path composed of
directory and file name elements, which can be absolute
or relative. It can also include a root component that
identifies the file system hierarchy.

## Key Features and Methods

Creating a Path: `Paths.get()` converts a `String`
representation of a path into a `Path` object:

```java
import java.nio.file.Path;
import java.nio.file.Paths;

Path myPath = Paths.get("C:\\example\\file.txt");
```

Once you have your `Path` object, the `Path` interface provides
useful methods:

1. `getFileName()` -> Returns the name of the file or directory
denoted by this path.
2. `getParent()` -> Returns the parent path, or `null` if this
path does not have a parent.
3. `getRoot()` -> Returns the root component.
4. `resolve()` -> Combine 2 Paths to create a new one.
5. `toFile()` -> Converts the `Path` object back to a
traditional `java.io.File` object.