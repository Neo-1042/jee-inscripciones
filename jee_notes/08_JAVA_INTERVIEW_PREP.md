# Java Interview Questions

## Overloading vs Overriding

**Overload** ---> When two methods or more have the same name
but differ in the type and/or number of arguments.

```java
public double computeArea(Circle c);
public double computeArea(Square s);
```

**Overriding** ---> Occurs when a method shares the same
name and function signature (type(s) + number of arguments) as
another method in its **super class**.

```java
public abstract class Shape {

    public void printMe() {
        System.out.println("I am a shape!");
    }

    public abstract double computeArea();
}

public class Circle extends Shape {

    private double rad = 5;
    // Override super class' printMe() method
    public void printMe() {
        System.out.println("I am a circle!");
    }
    
    public double computeArea() {
        return rad * rad * Math.PI;
    }
}

// This class does NOT override printMe()
public class Ambiguous extends Shape {

    private double area = 10;

    public double computeArea() {
        return area;
    }
}

public class DemoOverriding {

    public static void main(String[] args) {

        Shape[] shapes = new Shape[2];
        Circle circle = new Circle();
        Ambiguous amb = new Ambiguous();

        shapes[0] = circle;
        shapes[1] = ambiguous;

        for (Shape s : shapes) {
            s.printMe();
            System.out.println(s.computeArea());
        }
    }
}
```

# Java's Collection Framework

Here are some of the most useful items in Java's Collection
Framework:

### `ArrayList`

An `ArrayList` is a dynamically resizing array, which grows
as you insert elements.

```java
ArrayList<String> myArrL = new ArrayList<String>();
myArrL.add("First");
myArrL.add("Second");
myArrL.add("Third");

System.out.println(myArrL.get(0)); // Prints "First"
```

Both `ArrayList` and `Vector` implement the `List` interface.
However, `ArrayList` is usually preferred because of its
**faster performance**.

### `Vector` (legacy code)

A `Vector` is very similar to an `ArrayList`, except that
its methods are **synchronized**.

```java
Vector<String> myVec = new Vector<String>();
myVec.add("First");
myVec.add("Second");
myVec.add("Third");

System.out.println(myVec.get(0)); // Prints "First"
```

`Vector` has the advantage that it is **thread-safe** because
its methods are synchronized, meaning that Java ensures that
**only one thread at a time can execute that method on a given
object**.

Think of synchronization as putting a **lock** on the object.
If two threads try to add elements to an `ArrayList` (not
thread-safe), they could interfere with each other while
the `ArrayList` is resizing its internal array, potentially
causing:

- Lost data
- Corrupted state
- Unexpected exceptions

However, for modern Java applications, if thread safety is
needed, developers typically use:
```java
List<String> list = Collections.synchronizedList(new ArrayList<>());
```

### `LinkedList`

```java
LinkedList<String> myLL = new LinkedList<String>();

myLL.add("two");
// LinkedLists allow you to add elements to any position:
myLL.addFirst("one");

Iterator<String> iter = myLL.iterator();
while(iter.hasNext()) {
    System.out.println(iter.next());
}
```

### `HashMap`

```java
HashMap<String, String> map = new HashMap<String, String>();
map.put("one", "uno");
map.put("two", "dos");
```