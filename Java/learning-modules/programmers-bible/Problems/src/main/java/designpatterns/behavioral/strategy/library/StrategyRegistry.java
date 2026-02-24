package designpatterns.behavioral.strategy.library;

import designpatterns.behavioral.strategy.client.models.Product;
import designpatterns.behavioral.strategy.library.contract.SortStrategy;
import designpatterns.behavioral.strategy.library.impl.compound.SortByPriceThenDiscount;
import designpatterns.behavioral.strategy.library.impl.compound.SortByPriceThenQuantity;
import designpatterns.behavioral.strategy.library.impl.simple.SortByDiscount;
import designpatterns.behavioral.strategy.library.impl.simple.SortByPrice;
import designpatterns.behavioral.strategy.library.impl.simple.SortByQuantity;

import java.util.*;

public class StrategyRegistry {

    private static final Map<ProductSortingStrategies, SortStrategy<Product>> strategies = new HashMap<>();

    private StrategyRegistry() {
        throw new UnsupportedOperationException("Cannot instantiate StrategyRegistry");
    }

    static {
        populateMap();
    }

    public static Map<ProductSortingStrategies, SortStrategy<Product>> getStrategies() {
        return Collections.unmodifiableMap(strategies);
    }

    private static void populateMap() {
        strategies.put(ProductSortingStrategies.SORT_BY_PRICE, new SortByPrice());
        strategies.put(ProductSortingStrategies.SORT_BY_DISCOUNT, new SortByDiscount());
        strategies.put(ProductSortingStrategies.SORT_BY_QUANTITY, new SortByQuantity());
        strategies.put(ProductSortingStrategies.SORT_BY_PRICE_THEN_DISCOUNT, new SortByPriceThenDiscount());
        strategies.put(ProductSortingStrategies.SORT_BY_PRICE_THEN_QUANTITY, new SortByPriceThenQuantity());
    }

    public static List<Product> sort(ProductSortingStrategies strategy, List<Product> products) {
        SortStrategy<Product> sortingStrategy = strategies.get(strategy);
        List<Product> sortedProducts = new ArrayList<>(products);
        sortedProducts.sort(sortingStrategy.sortComparator());
        return sortedProducts;
    }

}
