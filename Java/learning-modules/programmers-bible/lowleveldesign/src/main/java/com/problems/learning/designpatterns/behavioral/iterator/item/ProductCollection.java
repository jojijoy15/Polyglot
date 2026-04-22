package com.problems.learning.designpatterns.behavioral.iterator.item;

import com.problems.learning.designpatterns.behavioral.iterator.traverse.Iterator;

import java.util.Comparator;

/**
 * Every collection that supports iteration implements this.
 * The factory method createIterator() decouples the collection
 * from any specific traversal strategy — different iterators
 * can be returned without changing the collection class.
 */
public interface ProductCollection {
    Iterator<Product> createIterator();              // default traversal

    Iterator<Product> createFilteredIterator(ProductFilter filter);  // filtered

    Iterator<Product> createSortedIterator(Comparator<Product> comparator); // sorted

    int size();
}