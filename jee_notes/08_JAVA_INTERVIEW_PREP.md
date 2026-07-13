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
