package com.problems.learning.designpatterns.behavioral.strategy.client;


import com.problems.learning.designpatterns.behavioral.strategy.client.data.Generator;
import com.problems.learning.designpatterns.behavioral.strategy.client.models.Product;
import com.problems.learning.designpatterns.behavioral.strategy.library.ProductSortingStrategies;
import com.problems.learning.designpatterns.behavioral.strategy.library.StrategyRegistry;

import java.util.List;
import java.util.logging.Logger;

public class ProductSorterClient {

    public static final Logger logger = Logger.getLogger(ProductSorterClient.class.getName());

    public static void main(String[] args) {
        List<Product> products = Generator.generateProducts();
        logger.info("Before sorting : " + products);
        List<Product> sorted = StrategyRegistry.sort(ProductSortingStrategies.SORT_BY_PRICE_THEN_DISCOUNT, products);
        logger.info("After sorting : " + sorted);
    }

}
