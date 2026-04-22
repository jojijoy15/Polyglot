package com.problems.learning.designpatterns.behavioral.template.pipeline;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PROBLEM SOLVED:
 * CsvDataPipeline only needs to answer THREE questions:
 * 1. How do I read a CSV?
 * 2. How do I validate CSV rows?
 * 3. How do I transform CSV rows?
 * 4. Where do I save the result?
 * <p>
 * It does NOT need to know about pipeline orchestration, reporting,
 * or execution order — the base class owns all of that.
 */
public class CsvDataPipeline extends DataPipeline {

    private static final int EXPECTED_COLUMNS = 4;
    public static final Logger logger = Logger.getLogger(CsvDataPipeline.class.getName());

    @Override
    protected List<String> readData(String source) {
        System.out.println("📂 [CSV] Reading file: " + source);

        // Simulated CSV rows (in real code: Files.readAllLines(Paths.get(source)))
        try {
            URI csvURI = getClass().getClassLoader()
                    .getResource("behavioral/template/employee.csv").toURI();
            return Files.readAllLines(Paths.get(csvURI));
        } catch (IOException | URISyntaxException e) {
            logger.log(Level.WARNING, "Exception occurred while reading csv file", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    protected boolean validate(List<String> rawData) {
        System.out.println("🔍 [CSV] Validating " + rawData.size() + " rows...");

        // Pipeline fails entirely if more than 50% rows are malformed
        long validRows = rawData.stream()
                .filter(row -> row.split(",").length == EXPECTED_COLUMNS)
                .count();

        double validRatio = (double) validRows / rawData.size();
        System.out.printf("   Valid ratio: %.0f%%%n", validRatio * 100);

        if (validRatio < 0.50) {
            System.out.println("   ❌ Too many malformed rows — rejecting entire file");
            return false;
        }

        System.out.println("   ✅ Validation passed");
        return true;
    }

    @Override
    protected List<String> transform(List<String> rawData) {
        System.out.println("⚙️  [CSV] Transforming rows...");

        List<String> result = new ArrayList<>();
        for (String row : rawData) {
            String[] cols = row.split(",");

            // Skip malformed rows silently
            if (cols.length != EXPECTED_COLUMNS) {
                System.out.println("   ⚠️  Skipping malformed row: " + row);
                continue;
            }
            // Skip rows with empty name
            if (cols[1].isBlank()) {
                System.out.println("   ⚠️  Skipping row with missing name: " + row);
                continue;
            }

            // Normalize: uppercase name, format salary with $
            String transformed = String.format("ID=%s | Name=%-10s | Dept=%-12s | Salary=$%s",
                    cols[0], cols[1].toUpperCase(), cols[2], cols[3]);

            result.add(transformed);
            System.out.println("   ✅ " + transformed);
        }
        return result;
    }

    @Override
    protected void save(List<String> processedData) {
        // In real code: write to output CSV file
        System.out.println("💾 [CSV] Saving " + processedData.size()
                + " records to output/employees.csv");
    }
}