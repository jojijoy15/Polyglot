package designpatterns.behavioral.strategy.library.impl.compound;

import designpatterns.behavioral.strategy.client.models.Product;
import designpatterns.behavioral.strategy.library.contract.SortStrategy;

import java.util.Comparator;

public class SortByPriceThenDiscount implements SortStrategy<Product> {

    @Override
    public Comparator<Product> sortComparator() {
        return Comparator.comparing(Product::getPrice).thenComparing(Product::getDiscount);
    }
}
