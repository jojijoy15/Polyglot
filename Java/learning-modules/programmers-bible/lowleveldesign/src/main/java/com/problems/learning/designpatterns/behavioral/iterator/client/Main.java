package com.problems.learning.designpatterns.behavioral.iterator.client;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;
import com.problems.learning.designpatterns.behavioral.iterator.item.ProductCatalog;
import com.problems.learning.designpatterns.behavioral.iterator.item.ProductFilter;
import com.problems.learning.designpatterns.behavioral.iterator.traverse.CompositeIterator;
import com.problems.learning.designpatterns.behavioral.iterator.util.IteratorUtils;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ── Build two vendor catalogs ─────────────────────────────────
        ProductCatalog techCatalog = new ProductCatalog("TechVendor", 10);
        techCatalog.addProduct(new Product("T001", "MacBook Pro 16", "Laptops", 2499.00, 15, 4.8));
        techCatalog.addProduct(new Product("T002", "Dell XPS 15", "Laptops", 1799.00, 8, 4.6));
        techCatalog.addProduct(new Product("T003", "Sony WH-1000XM5", "Headphones", 349.00, 42, 4.9));
        techCatalog.addProduct(new Product("T004", "Logitech MX Master 3", "Accessories", 99.00, 0, 4.7));
        techCatalog.addProduct(new Product("T005", "Samsung 4K Monitor", "Monitors", 599.00, 20, 4.5));

        ProductCatalog homeCatalog = new ProductCatalog("HomeVendor", 10);
        homeCatalog.addProduct(new Product("H001", "Dyson V15 Vacuum", "Cleaning", 749.00, 5, 4.7));
        homeCatalog.addProduct(new Product("H002", "Instant Pot Pro", "Kitchen", 129.00, 33, 4.8));
        homeCatalog.addProduct(new Product("H003", "Philips Air Fryer", "Kitchen", 89.00, 0, 4.4));
        homeCatalog.addProduct(new Product("H004", "Roomba i7+", "Cleaning", 599.00, 12, 4.6));
        homeCatalog.addProduct(new Product("H005", "Nest Thermostat", "Smart Home", 249.00, 18, 4.5));

        // ── Scenario 1: Forward traversal ────────────────────────────
        IteratorUtils.print(
                techCatalog.createIterator(),
                "Scenario 1: All Tech Products (Forward)");

        // ── Scenario 2: Reverse traversal ────────────────────────────
        IteratorUtils.print(
                techCatalog.createReverseIterator(),
                "Scenario 2: All Tech Products (Reverse)");

        // ── Scenario 3: Filtered — in stock under $500 ────────────────
        ProductFilter affordable = ProductFilter.inStock()
                .and(ProductFilter.maxPrice(500));

        IteratorUtils.print(
                techCatalog.createFilteredIterator(affordable),
                "Scenario 3: In-Stock Tech Products Under $500");

        // ── Scenario 4: Filtered — top-rated Kitchen items ────────────
        ProductFilter topRatedKitchen = ProductFilter.byCategory("Kitchen")
                .and(ProductFilter.minRating(4.5));

        IteratorUtils.print(
                homeCatalog.createFilteredIterator(topRatedKitchen),
                "Scenario 4: Top-Rated Kitchen Products (≥ 4.5★)");

        // ── Scenario 5: Sorted by price ascending ─────────────────────
        IteratorUtils.print(
                homeCatalog.createSortedIterator(
                        Comparator.comparingDouble(Product::getPrice)),
                "Scenario 5: Home Products Sorted by Price ↑");

        // ── Scenario 6: Sorted by rating descending ───────────────────
        IteratorUtils.print(
                techCatalog.createSortedIterator(
                        Comparator.comparingDouble(Product::getRating).reversed()),
                "Scenario 6: Tech Products Sorted by Rating ↓");

        // ── Scenario 7: Composite — traverse both catalogs as one ─────
        CompositeIterator allProducts = new CompositeIterator(List.of(
                techCatalog.createIterator(),
                homeCatalog.createIterator()
        ));
        IteratorUtils.print(allProducts, "Scenario 7: All Products (Both Catalogs)");

        // ── Scenario 8: Composite + Filtered — in-stock across both ───
        CompositeIterator inStockAll = new CompositeIterator(List.of(
                techCatalog.createFilteredIterator(ProductFilter.inStock()),
                homeCatalog.createFilteredIterator(ProductFilter.inStock())
        ));
        System.out.println("\n📊 Total in-stock count : "
                + IteratorUtils.count(inStockAll));

        // ── Scenario 9: Inventory value calculation ───────────────────
        double techValue = IteratorUtils.totalValue(techCatalog.createIterator());
        double homeValue = IteratorUtils.totalValue(homeCatalog.createIterator());
        System.out.printf("%n💰 Inventory Value — Tech: $%,.2f | Home: $%,.2f | Total: $%,.2f%n",
                techValue, homeValue, techValue + homeValue);
    }
}