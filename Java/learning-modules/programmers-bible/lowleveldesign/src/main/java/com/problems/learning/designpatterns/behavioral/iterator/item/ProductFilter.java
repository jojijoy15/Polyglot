package com.problems.learning.designpatterns.behavioral.iterator.item;

/**
 * PROBLEM SOLVED:
 * Instead of hardcoding filter conditions inside iterators,
 * we pass the filter as a strategy. Any filtering criteria
 * can be combined without creating a new iterator class per filter.
 */
@FunctionalInterface
public interface ProductFilter {
    boolean test(Product product);

    // Built-in filter factories for common use cases
    static ProductFilter inStock() {
        return Product::isInStock;
    }

    static ProductFilter byCategory(String category) {
        return p -> p.getCategory().equalsIgnoreCase(category);
    }

    static ProductFilter maxPrice(double maxPrice) {
        return p -> p.getPrice() <= maxPrice;
    }

    static ProductFilter minRating(double minRating) {
        return p -> p.getRating() >= minRating;
    }

    // Compose filters with AND
    default ProductFilter and(ProductFilter other) {
        return p -> this.test(p) && other.test(p);
    }

    // Compose filters with OR
    default ProductFilter or(ProductFilter other) {
        return p -> this.test(p) || other.test(p);
    }
}