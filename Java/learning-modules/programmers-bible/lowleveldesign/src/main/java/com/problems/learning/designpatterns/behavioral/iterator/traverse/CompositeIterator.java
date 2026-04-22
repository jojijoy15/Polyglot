package com.problems.learning.designpatterns.behavioral.iterator.traverse;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * PROBLEM SOLVED:
 * When you have products from multiple vendors/catalogs, you need to
 * iterate all of them as if they were one flat collection.
 * <p>
 * Without CompositeIterator, the client would need to:
 * for (Catalog c : catalogs) {
 * Iterator it = c.createIterator();
 * while (it.hasNext()) { process(it.next()); }
 * }
 * <p>
 * With CompositeIterator, the client just calls hasNext()/next()
 * on ONE iterator that transparently walks across all catalogs.
 */
public class CompositeIterator implements Iterator<Product> {

    private final List<Iterator<Product>> iterators;
    private int currentIteratorIndex;

    public CompositeIterator(List<Iterator<Product>> iterators) {
        this.iterators = iterators;
        this.currentIteratorIndex = 0;
    }

    @Override
    public boolean hasNext() {
        // Fast-forward past exhausted iterators
        while (currentIteratorIndex < iterators.size()) {
            if (iterators.get(currentIteratorIndex).hasNext()) {
                return true;
            }
            currentIteratorIndex++;   // this iterator is done — move to next
        }
        return false;
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException("All catalogs exhausted");
        return iterators.get(currentIteratorIndex).next();
    }

    @Override
    public void reset() {
        currentIteratorIndex = 0;
        iterators.forEach(Iterator::reset);
    }
}