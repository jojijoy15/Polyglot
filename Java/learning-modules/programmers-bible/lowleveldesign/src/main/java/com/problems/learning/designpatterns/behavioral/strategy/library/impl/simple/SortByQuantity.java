package com.problems.learning.designpatterns.behavioral.strategy.library.impl.simple;

import com.problems.learning.designpatterns.behavioral.strategy.client.models.Product;
import com.problems.learning.designpatterns.behavioral.strategy.library.contract.SortStrategy;

import java.util.Comparator;

public class SortByQuantity implements SortStrategy<Product> {

    @Override
    public Comparator<Product> sortComparator() {
        return (a, b) -> a.getQuantity() > b.getQuantity() ? 1 : a.getQuantity() == b.getQuantity() ? 0 : -1;
    }
}
