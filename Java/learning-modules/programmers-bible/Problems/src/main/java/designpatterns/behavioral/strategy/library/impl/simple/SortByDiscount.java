package designpatterns.behavioral.strategy.library.impl.simple;

import designpatterns.behavioral.strategy.client.models.Product;
import designpatterns.behavioral.strategy.library.contract.SortStrategy;

import java.util.Comparator;

public class SortByDiscount implements SortStrategy<Product> {

    @Override
    public Comparator<Product> sortComparator() {
        return  (a, b) -> a.getDiscount() > b.getDiscount()
                ? 1 : a.getDiscount() == b.getDiscount()
                ? 0 : -1;
    }
}
