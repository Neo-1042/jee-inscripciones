# Java's "Optional" Class

Introduced from Java SE 8, the `Optional` class provides a
type-level solution for representing optional values
instead of null references.

To create an optional object, we can use the following static
methods:

`empty()`, `of()`
```java
@Test
public void whenCreatesEmptyOptional_thenCorrect() {

    // empty() is a static method:
    Optional<String> empty = Optional.empty();
    // A value is present only if we've created Optional
    // with a non-null value.
    assertFalse(empty.isPresent());
}

@Test
public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
    String name = "baeldung";
    // name can NOT be null. Otherwise, we'll get a 
    // NullPointerException
    Optional<String> opt = Optional.of(name);
    assertTrue(opt.isPresent());
}
```

In case we actually expect some null values, use the
`ofNullable()` method:
```java
@Test
public void givenNonNull_whenCreatesNullable_thenCorrect() {
    String name = "baeldung";
    // If null, ofNullable() returns an empty Optional object.
    Optional<String> opt = Optional.ofNullable(name);
    assertTrue(opt.isPresent());    
}
```

## Checking Value Presence: `isPresent()` and `isEmpty()`

```java
public void givenOptional_whenIsPresentWorks_thenCorrect() {
    Optional<String> opt = Optional.of("Baeldung");
    // Check whether 'opt' holds a value or not:
    assertTrue(opt.isPresent());

    // From Java 11, do the opposite with isEmpty()
    assertFalse(opt.isEmpty());

    opt = Optional.ofNullable(null);
    assertFalse(opt.isPresent());
}
```

## Conditional Action with `ifPresent()`

Before `Optional`, we would do:
```java
// This code is prone to error, since we might forget
// to write again that validation later in the code.
if (name != null) {
    System.out.println(name.length());
}
// If a null value finds its way into that code, we'll
// have a NullPointerException at runtime.
```

> `Optional` makes us deal with nullable values explicitly
as a way of enforcing good programming practices.

```java
@Test
public void givenOptional_whenIfPresentWorks_thenCorrect() {
    Optional<String> opt = Optional.of("baeldung");
    // Lambda expression
    opt.ifPresent(name -> System.out.println(name.length()))
}
```

## Default Value with `orElse()`

The `orElse()` method is used to retrieve the value wrapped
inside an `Optional` instance. It takes one parameter, which
acts as a default value. The `orElse()` method returns the
wrapped value if it's present, and its argument otherwise:
```java
@Test
public void whenOrElseWorks_thenCorrect() {
    String nullName = null;
    String name = Optional.ofNullable(nullName).orElse("john");
    assertEquals("john", name); // TRUE
}
```

## Default Value with `orElseGet()`

Instead of taking a value to return if the `Optional` value
is not present, `orElseGet()` takes a 
**supplier functional interface**, which is invoked and returns
the value of the invocation:
```java
@Test
public void whenOrElseGetWorks_thenCorrect() {
    String nullName = null;
    String name = Optional.ofNullable(nullName).orElseGet(() -> "john");

    assertEquals("john", name);
}
```

### `orElse()` vs `orElseGet()`

When the wrapped value is not present, then both methods work
exactly the same way.

## Exceptions with `orElseThrow()`

The `orElseThrow()` method follows from `orElse()` and
`orElseGet()` and adds a new approach for handling an
absent value.

Instead of returning a default value when the wrapped value
is not present, it throws an exception:
```java
@Test(expected = IllegalArgumentException.class)
public void whenOrElseThrowWorks_thenCorrect() {

    String nullName = null;
    String name = Optional.ofNullable(nullName)
        .orElseThrow(IllegalArgumentException::new);
}

// Java 10 introduced a simplified no-arg version of the
// orElseThrow() method. In case of an empty Optional,
// it throws a NoSuchElementException:
@Test(expected = NoSuchElementException.class)
public void whenNoArgOrElseThrowWorks_thenCorrect() {
    String nullName = null;
    String name = Optional.ofNullable(nullName).orElseThrow();
}
```

## Returning a Value with `get()` (not recommended)

```java
@Test(expected = NoSuchElementException.class)
public void givenOptional_whenGetsValue_thenCorrect() {
    Optional<String> opt = Optional.of("baeldung");
    String nameFromGet = opt.get();
    assertEquals("baeldung", name);
}
```

## Conditional Return with `filter()`

The `filter()` method takes a predicate as argument and returns
an `Optional` object. If the wrapped value passes testing by
the predicate, then the `Optional` is returned as-is.
If the predicates returns false, then it will return an
empty `Optional`.

```java
@Test
public void whenOptionalFilterWorks_thenCorrect() {
    Integer year = 2026;
    Optional<Integer> yearOptional = Optional.of(year);
    boolean is2026 = yearOptional.filter(y -> y == 2026)
        .isPresent();
    assertTrue(is2026);

    boolean is2027 = yearOptional.filter(y -> y == 2027)
        .isPresent();
    assertFalse(is2027);
}
```

The `filter()` method is normally used this way to reject
wrapped values based on a predefined rule. We could use
it to reject a **wrong email format** or a
**password that is not strong enough**.