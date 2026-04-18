Source = "Java, A Beginners Guide" - Herbert Schildt
> Reminder: Object-Oriented Programming:

1. **Encapsulation** --> A programming mechanism that binds together code
and the data it manipulates, and that keeps both safe from
outside interference and misuse. Java's basic unit of encapsulation is the class.
2. **Polymorphism** --> The quality that allows one interface to access a general
class of actions. "One interface, multiple methods". (e.g. same stack
operations for different kinds of stacks)
3. **Inheritance** --> The process by which one object can acquire the properties
and methods of its parent object.

# Multithreaded Programming

A multithreaded program contains two or more parts that can 
run **concurrently**.
Each part of such program is called a **thread**, and each
thread defines a separate path of execution.

## Process-Based Multitasking

**Process** --> (Plain and simple) A program that is executing.  
Process-Based MultiTasking --> A mechanism that allows your
computer to run two or more programs concurrently.
(e.g. running the JVM and the text editor at the same time).
A program is the smallest unit of code that can be
dispatched by the scheduler. Process-based multitasking
is not under the control of Java.

## Thread-Based Multitasking

In a thread-based multitasking environment, the **thread** is
the smallest unit of dispatchable code. This means that a
single program can perform **two or more tasks** at once.
e.g. a text editor can be formatting text at the same time
that it is printing, as long as these two actions are being
performed by two separate threads.
Only **thread-based multitasking** can be under control of
Java.

## Advantages of Multithreaded Programming

> The main advantage of multithreaded programming is that it
lets you utilize the idle time that is present in most
programs.

Besides, most I/O devices; network ports, disk drives, the
keyboard, etc. are much slower than the CPU. Thus,
a program will spend a majority of its execution time
waiting to send or receive information to or from a device.
With multithreaded programming, while one part of your program
is sending a file over the internet, another part can be
reading keyboard input, and still another can be buffering
the next block of data to send.

## Single-core and Multicore/Multiprocessor Systems

Java's multithreading features work in both single-processor
and multiprocessor/multicore systems.

In a **single-core system**, concurrently executing threads
share the CPU, with each thread receving a slice of CPU time.
Therefore, in a single-core system, two or more threads do not
actually run at the same time, but idle CPU time is utilized.

In a **multiprocessor/multicore** system, it is possible for
two or more threads to actually execute simultaneously.
In many cases, this can further improve program efficiency
and increase the speed of certain operations.

## States of a Thread

- Running
- Ready to Run (as soon as it gets CPU time)
- Suspended (temporary halt)
- Resumed (from being suspended)
- Blocked (waiting for a resource)
- Terminated (cannot be resumed)

## Synchronization

A mechanism that allows the execution of threads to be
coordinated in certain well-defined ways. Java has a complete
subsystem devoted to synchronization.

# The `Thread` Class and `Runnable` Interface

Java's multithreading system is built upon the `Thread`
class and its companion, the `Runnable` interface.
Both belong to `java.lang.*`

To create a new thread, your program will either:
- `extends Thread`, or
- `implements Runnable`

### Methods from the `Thread` class

| Method    | Description |
| :--- | :--- |
| `final String getName()` | Get a thread's name |
| `final int getPriority()` | Get a thread's priority |
| `final boolean isAlive()` | Is the thread still running? |
| `final void join()` | Waits for a thread to terminate |
| `void run()` | Entry point for the thread |
| `static void sleep(long)` | Suspend a thread for a specific amount of milliseconds |
| `void start()` | Starts a thread by calling its `run()` method |

All processes have at least one thread of execution, which is
usually called the `main thread`, because it is the one that
is executed when your program begins. Thus, the main thread
is the thread that all of the basic examples of a Java
program uses.

From the **main thread**, you can create other threads.

# Creating a Thread

The `Thread` class encapsulates an object that is runnable.

Recall, to create a new thread, your program will either:
- `extends Thread`, or
- `implements Runnable`

But both methods still use the `Thread` class to
instantiate, access, and control the thread. The only
difference is how a thread-enabled class is created.

The `Runnable` interface abstracts a unit of executable
code. You can construct a thread on any object that
implements the `Runnable` interface, which only
declares the following method:

```java
/* Inside run(), you will define the code that constitutes
   the new thread. run() can call other methods, use other
   classes, and declare variables just like the main thread.
   The only difference is that run() establishes the entry
   point for another, concurrent thread of execution
   within your program.
   This new thread will end when run() returns.
*/
public void run();
```

After you have created a class that implements `Runnable`,
you will instantiate an object of type `Thread` on an
object of that class. We will use this constructor:
```java
Thread myThread = new Thread(Runnable threadOb);
/* In this constructor, threadOb is an instance of a class that
 the Runnable interface. This defines where execution of
the thread will begin. Once created, the new thread will not
start running until you call its start() method, which is
declared within Thread.
In essence, start() executes a call to run()
*/
```

## Example. Creating a new thread and start running it

```java

// Create a thread bt implementing Runnable:

class MyThread implements Runnable {
    String thrdName;

    MyThread(String name) {
        this.thrdName = name;
    }

    // Entry point of the Thread
    public void run() {
        System.out.println(thrdName + " starting ...");

        try {
            for(int count=0; count < 10; count++) {
                Thread.sleep(400);
                System.out.println("In " + thrdName + ", count is = " + count);
            }
        } catch(InterruptedException exc) {
            System.out.println(thrdName + " interrupted.");
        }

        System.out.println(thrdName + " terminating.");
    }
}

class DemoThreads {

    public static void main(String[] args) {
        System.out.println("Main thread starting...");

        // First, construct a "MyThread" object, which is
        // a Runnable object
        MyThread mt = new MyThread("Child #1");

        // Next, construct a thread from that object.
        Thread newThread = new Thread(mt);

        // Finally, start the execution of that thread:
        newThread.start();

        for(int i=0; i<50; i++) {
            System.out.println(".");
            try {
                Thread.sleep(100);
            } catch (InterruptedException exc) {
                System.out.println("Main thread interrupted");
            }
        }

        System.out.println("Main thread ending");
    }
}

// This timing (math) ensures that main() finishes last.
// However, there are better ways for one thread to
// wait until another completes.
```

```java
// The sleep() method can throw an InterruptedException:
static void sleep(long ms) throws InterruptedException {}

// Thus, its execution must be wrapped inside a try block.
```

As a general rule, a program continues to run until all
of its threads have ended. Thus, having the main thread
finish last is not a requirement, but a good practice.