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



