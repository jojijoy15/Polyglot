package com.problems.learning.designpatterns.behavioral.template.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM SOLVED:
 * DB pipeline reads from a ResultSet-like structure — completely
 * different from file I/O or HTTP. But because it extends DataPipeline,
 * it automatically gets validation reporting, step sequencing, and
 * the report step — without writing any of that code itself.
 */
public class DbQueryPipeline extends DataPipeline {

    @Override
    protected List<String> readData(String source) {
        System.out.println("🗄️  [DB] Executing query on: " + source);

        // Simulated DB ResultSet rows: orderId|customerId|amount|status
        return List.of(
                "ORD-001|CUST-101|4500.00|COMPLETED",
                "ORD-002|CUST-102|1200.00|PENDING",
                "ORD-003|CUST-103|-500.00|COMPLETED",    // negative amount — bad data
                "ORD-004|CUST-104|8900.00|CANCELLED",
                "ORD-005|CUST-105|2300.00|COMPLETED",
                "ORD-006||3400.00|COMPLETED"             // missing customer ID
        );
    }

    @Override
    protected boolean validate(List<String> rawData) {
        System.out.println("🔍 [DB] Validating query results...");

        if (rawData.isEmpty()) {
            System.out.println("   ❌ Query returned no rows");
            return false;
        }

        // Ensure all rows have exactly 4 pipe-separated columns
        boolean allWellFormed = rawData.stream()
                .allMatch(row -> row.split("\\|").length == 4);

        if (!allWellFormed) {
            System.out.println("   ❌ Some rows have incorrect column count");
            // In real case might return false — here we tolerate and filter in transform
        }

        System.out.println("   ✅ ResultSet structure is valid");
        return true;
    }

    @Override
    protected List<String> transform(List<String> rawData) {
        System.out.println("⚙️  [DB] Processing order records...");

        List<String> result = new ArrayList<>();
        for (String row : rawData) {
            String[] cols = row.split("\\|");
            if (cols.length != 4) continue;

            String orderId = cols[0];
            String customerId = cols[1];
            String amount = cols[2];
            String status = cols[3];

            // Skip cancelled or missing customer ID orders
            if (status.equals("CANCELLED")) {
                System.out.println("   ⏭️  Skipping cancelled order: " + orderId);
                continue;
            }
            if (customerId.isBlank()) {
                System.out.println("   ⚠️  Skipping order with no customer: " + orderId);
                continue;
            }
            // Skip orders with negative amounts
            if (Double.parseDouble(amount) < 0) {
                System.out.println("   ⚠️  Skipping order with invalid amount: " + orderId);
                continue;
            }

            String transformed = String.format("Order=%-8s | Customer=%-9s | Amount=$%-9s | Status=%s",
                    orderId, customerId, amount, status);

            result.add(transformed);
            System.out.println("   ✅ " + transformed);
        }
        return result;
    }

    @Override
    protected void save(List<String> processedData) {
        // In real code: batch insert into reporting schema
        System.out.println("💾 [DB] Writing " + processedData.size()
                + " records to reporting.orders_summary");
    }

    // DB pipeline does NOT override enrich() — default no-op from base class is fine
}