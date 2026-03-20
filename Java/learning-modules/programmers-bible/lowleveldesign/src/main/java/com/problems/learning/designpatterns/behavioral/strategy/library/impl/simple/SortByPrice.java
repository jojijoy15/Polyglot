package com.problems.learning.designpatterns.behavioral.strategy.library.impl.simple;

import com.problems.learning.designpatterns.behavioral.strategy.client.models.Product;
import com.problems.learning.designpatterns.behavioral.strategy.library.contract.SortStrategy;

import java.util.Comparator;

public class SortByPrice implements SortStrategy<Product> {

    @Override
    public Comparator<Product> sortComparator() {
        return (a, b) -> a.getPrice() > b.getPrice() ? 1 : a.getPrice() == b.getPrice() ? 0 : -1;
    }
}
