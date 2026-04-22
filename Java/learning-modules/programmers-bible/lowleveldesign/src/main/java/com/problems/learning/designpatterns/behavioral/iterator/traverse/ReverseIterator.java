package com.problems.learning.designpatterns.behavioral.iterator.traverse;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;

import java.util.NoSuchElementException;

/**
 * PROBLEM SOLVED:
 * Reverse traversal without exposing the array or adding a
 * reverseIterate() method to the collection class.
 * The collection stays unchanged — traversal strategy is external.
 */
public class ReverseIterator implements Iterator<Product> {

    private final Product[] products;
    private int cursor;

    public ReverseIterator(Product[] products) {
        this.products = products;
        this.cursor = products.length - 1;
    }

    @Override
    public boolean hasNext() {
        return cursor >= 0;
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException("No more products");
        return products[cursor--];
    }

    @Override
    public void reset() {
        cursor = products.length - 1;
    }
}