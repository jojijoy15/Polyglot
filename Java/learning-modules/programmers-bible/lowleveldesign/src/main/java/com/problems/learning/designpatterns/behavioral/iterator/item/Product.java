package com.problems.learning.designpatterns.behavioral.iterator.item;

public class Product {

    private final String id;
    private final String name;
    private final String category;
    private final double price;
    private final int stockQuantity;
    private final double rating;         // 0.0 - 5.0
    private final boolean inStock;

    public Product(String id, String name, String category,
                   double price, int stockQuantity, double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.rating = rating;
        this.inStock = stockQuantity > 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public double getRating() {
        return rating;
    }

    public boolean isInStock() {
        return inStock;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-25s | %-12s | $%7.2f | ⭐%.1f | Stock: %3d",
                id, name, category, price, rating, stockQuantity);
    }
}