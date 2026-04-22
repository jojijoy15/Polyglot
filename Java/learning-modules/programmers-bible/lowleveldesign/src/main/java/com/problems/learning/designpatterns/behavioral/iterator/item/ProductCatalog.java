package com.problems.learning.designpatterns.behavioral.iterator.item;

import com.problems.learning.designpatterns.behavioral.iterator.traverse.*;

import java.util.Arrays;
import java.util.Comparator;

/**
 * PROBLEM SOLVED:
 * ProductCatalog stores products internally as an array.
 * The client never knows this — it only works with iterators.
 * We can change internal storage to a TreeMap or LinkedList
 * with zero changes to any client code.
 */
public class ProductCatalog implements ProductCollection {

    private Product[] products;
    private int size;
    private final String vendorName;

    public ProductCatalog(String vendorName, int capacity) {
        this.vendorName = vendorName;
        this.products = new Product[capacity];
        this.size = 0;
    }

    public void addProduct(Product product) {
        if (size >= products.length) {
            // Grow the array — client code is unaffected
            products = Arrays.copyOf(products, products.length * 2);
        }
        products[size++] = product;
    }

    // ── Iterator factory methods ──────────────────────────────────────

    @Override
    public Iterator<Product> createIterator() {
        return new ForwardIterator(Arrays.copyOf(products, size));
    }

    public Iterator<Product> createReverseIterator() {
        return new ReverseIterator(Arrays.copyOf(products, size));
    }

    @Override
    public Iterator<Product> createFilteredIterator(ProductFilter filter) {
        return new FilteredIterator(Arrays.copyOf(products, size), filter);
    }

    @Override
    public Iterator<Product> createSortedIterator(Comparator<Product> comparator) {
        return new SortedIterator(Arrays.copyOf(products, size), comparator);
    }

    @Override
    public int size() {
        return size;
    }

    public String getVendorName() {
        return vendorName;
    }
}