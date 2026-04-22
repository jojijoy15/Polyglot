package com.problems.learning.designpatterns.behavioral.iterator.traverse;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;
import com.problems.learning.designpatterns.behavioral.iterator.item.ProductFilter;

import java.util.NoSuchElementException;

/**
 * PROBLEM SOLVED:
 * Filtering without modifying the original collection or creating
 * a filtered copy. The iterator skips non-matching elements lazily
 * — only evaluating the filter as elements are requested.
 * <p>
 * Client code:
 * Iterator<Product> it = catalog.createFilteredIterator(
 * ProductFilter.inStock().and(ProductFilter.maxPrice(100))
 * );
 * <p>
 * No knowledge of internal structure required.
 */
public class FilteredIterator implements Iterator<Product> {

    private final Product[] products;
    private final ProductFilter filter;
    private int cursor;
    private Product nextProduct;   // prefetched — supports hasNext() lookahead

    public FilteredIterator(Product[] products, ProductFilter filter) {
        this.products = products;
        this.filter = filter;
        this.cursor = 0;
        advance();   // prefetch first matching element
    }

    // Advance cursor to next element that passes the filter
    private void advance() {
        nextProduct = null;
        while (cursor < products.length) {
            Product candidate = products[cursor++];
            if (filter.test(candidate)) {
                nextProduct = candidate;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextProduct != null;
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException("No more matching products");
        Product result = nextProduct;
        advance();   // prefetch the next matching element
        return result;
    }

    @Override
    public void reset() {
        cursor = 0;
        nextProduct = null;
        advance();
    }
}