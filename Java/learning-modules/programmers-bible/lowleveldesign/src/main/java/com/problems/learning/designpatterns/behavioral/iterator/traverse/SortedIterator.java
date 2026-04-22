package com.problems.learning.designpatterns.behavioral.iterator.traverse;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * PROBLEM SOLVED:
 * Sorted traversal without modifying the original collection.
 * The sort is done on a copy — original order is preserved.
 * Comparator is injected — any sort order works without
 * adding new methods to the collection class.
 */
public class SortedIterator implements Iterator<Product> {

    private final Product[] sortedProducts;
    private int cursor;

    public SortedIterator(Product[] products, Comparator<Product> comparator) {
        // Sort a COPY — never mutate the original collection
        this.sortedProducts = Arrays.copyOf(products, products.length);
        Arrays.sort(this.sortedProducts, comparator);
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return cursor < sortedProducts.length;
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException("No more products");
        return sortedProducts[cursor++];
    }

    @Override
    public void reset() {
        cursor = 0;
    }
}