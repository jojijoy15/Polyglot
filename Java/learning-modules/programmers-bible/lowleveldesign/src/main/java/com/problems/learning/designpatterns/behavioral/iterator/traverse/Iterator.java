package com.problems.learning.designpatterns.behavioral.iterator.traverse;

/**
 * PROBLEM SOLVED:
 * Without Iterator, the client must know HOW the collection is stored
 * internally to traverse it:
 * <p>
 * for (int i = 0; i < catalog.getArray().length; i++) { ... }  // knows it's an array
 * for (Map.Entry e : catalog.getMap().entrySet()) { ... }      // knows it's a Map
 * <p>
 * Problems:
 * 1. Client is tightly coupled to the internal data structure
 * 2. Changing storage (array → TreeMap) breaks ALL client code
 * 3. Multiple traversal strategies (filtered, sorted) require
 * exposing the raw collection and duplicating logic everywhere
 * 4. Cannot traverse multiple collections uniformly
 * <p>
 * Iterator encapsulates traversal logic behind a uniform interface.
 * The client only knows: hasNext() and next() — nothing else.
 */
public interface Iterator<T> {
    boolean hasNext();           // is there a next element?

    T next();                    // return next and advance cursor

    default void reset() {
    }      // optional — restart from beginning
}