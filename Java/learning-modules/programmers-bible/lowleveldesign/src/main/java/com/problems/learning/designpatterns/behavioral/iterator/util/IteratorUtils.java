package com.problems.learning.designpatterns.behavioral.iterator.util;

import com.problems.learning.designpatterns.behavioral.iterator.item.Product;
import com.problems.learning.designpatterns.behavioral.iterator.traverse.Iterator;

/**
 * PROBLEM SOLVED:
 * Common iteration operations (print, count, find, collect)
 * written once — work with ANY Iterator<Product> regardless
 * of the concrete type behind it.
 */
public class IteratorUtils {

    public static void print(Iterator<Product> iterator, String label) {
        System.out.println("\n📦 " + label + " ─────────────────────────────────────────────────────");
        System.out.println("   " + String.format("%-6s | %-25s | %-12s | %-9s | %-6s | %s",
                "ID", "Name", "Category", "Price", "Rating", "Stock"));
        System.out.println("   " + "─".repeat(85));

        int count = 0;
        while (iterator.hasNext()) {
            System.out.println("   " + iterator.next());
            count++;
        }

        System.out.println("   " + "─".repeat(85));
        System.out.println("   Total: " + count + " products");
    }

    public static int count(Iterator<Product> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static double totalValue(Iterator<Product> iterator) {
        double total = 0;
        while (iterator.hasNext()) {
            Product p = iterator.next();
            total += p.getPrice() * p.getStockQuantity();
        }
        return total;
    }
}