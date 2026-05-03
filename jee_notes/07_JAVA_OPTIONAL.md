# Java's "Optional" Class

Introduced from Java SE 8, the `Optional` class provides a
type-level solution for representing optional values
instead of null references.

To create an optional object:
```java
@Test
public void whenCreatesEmptyOptional_thenCorrect() {

    // empty() is a static method:
    Optional<String> empty = Optional.empty();
    assertFalse(empty.isPresent());
}
```