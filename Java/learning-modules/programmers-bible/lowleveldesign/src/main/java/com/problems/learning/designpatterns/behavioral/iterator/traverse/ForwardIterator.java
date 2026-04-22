package com.problems.learning.designpatterns.behavioral.iterator.traverse;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;

import java.util.NoSuchElementException;

/**
 * PROBLEM SOLVED:
 * Standard sequential traversal over an array-backed collection.
 * Even if the backing store changes from array to LinkedList,
 * the client code using ForwardIterator stays unchanged.
 */
public class ForwardIterator implements Iterator<Product> {

    private final Product[] products;
    private int cursor;

    public ForwardIterator(Product[] products) {
        this.products = products;
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return cursor < products.length;
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException("No more products");
        return products[cursor++];
    }

    @Override
    public void reset() {
        cursor = 0;
    }
}