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
 * JSON data arrives from a REST API — completely different reading
 * mechanism, different validation (schema check), different
 * transformation (field extraction). Yet it plugs into the same
 * pipeline skeleton without any changes to DataPipeline.
 */
public class JsonApiPipeline extends DataPipeline {

    public static final Logger logger = Logger.getLogger(JsonApiPipeline.class.getName());

    @Override
    protected List<String> readData(String source) {
        System.out.println("🌐 [JSON] Calling API: " + source);

        // Simulated JSON payloads (in real code: HttpClient + ObjectMapper)
        try {
            URI jsonURI = getClass().getClassLoader()
                    .getResource("behavioral/template/products.txt").toURI();
            return Files.readAllLines(Paths.get(jsonURI));
        } catch (IOException | URISyntaxException e) {
            logger.log(Level.WARNING, "Exception occurred while reading json file", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    protected boolean validate(List<String> rawData) {
        System.out.println("🔍 [JSON] Validating API response...");

        // Check that at least one valid JSON object exists
        boolean hasValidJson = rawData.stream()
                .anyMatch(row -> row.startsWith("{") && row.endsWith("}"));

        if (!hasValidJson) {
            System.out.println("   ❌ No valid JSON objects in response");
            return false;
        }

        System.out.println("   ✅ API response contains valid JSON");
        return true;
    }

    @Override
    protected List<String> transform(List<String> rawData) {
        System.out.println("⚙️  [JSON] Extracting and normalizing fields...");

        List<String> result = new ArrayList<>();
        for (String json : rawData) {
            // Skip non-JSON rows
            if (!json.startsWith("{")) {
                System.out.println("   ⚠️  Skipping invalid JSON: " + json);
                continue;
            }

            // Naive field extraction (real code would use ObjectMapper)
            String id = extractField(json, "id");
            String product = extractField(json, "product");
            String price = extractField(json, "price");
            String stock = extractField(json, "stock");

            // Business rule: skip empty names or negative prices
            if (product.isBlank()) {
                System.out.println("   ⚠️  Skipping product with no name: " + id);
                continue;
            }
            if (Double.parseDouble(price) < 0) {
                System.out.println("   ⚠️  Skipping product with negative price: " + id);
                continue;
            }

            String transformed = String.format("ID=%-4s | Product=%-10s | Price=$%-8s | Stock=%s",
                    id, product, price, stock);

            result.add(transformed);
            System.out.println("   ✅ " + transformed);
        }
        return result;
    }

    /**
     * PROBLEM SOLVED:
     * JSON pipeline needs a currency conversion step that CSV
     * and DB pipelines don't need. The HOOK method in the base
     * class provides this extension point — without forcing
     * CsvDataPipeline or DbQueryPipeline to implement it too.
     */
    @Override
    protected List<String> enrich(List<String> data) {
        System.out.println("💱 [JSON] Enriching: converting USD prices to INR (rate: 83.5)...");

        return data.stream()
                .map(row -> {
                    // Extract price value and convert (simplified)
                    if (row.contains("Price=$")) {
                        String[] parts = row.split("Price=\\$");
                        String priceStr = parts[1].split("\\s")[0].trim();
                        double inr = Double.parseDouble(priceStr) * 83.5;
                        return row.replace("Price=$" + priceStr,
                                String.format("Price=₹%-8.2f", inr));
                    }
                    return row;
                })
                .toList();
    }

    @Override
    protected void save(List<String> processedData) {
        // In real code: write to product catalog service or DB
        System.out.println("💾 [JSON] Pushing " + processedData.size()
                + " products to catalog service");
    }

    // Naive JSON field extractor — real code would use Jackson
    private String extractField(String json, String key) {
        String search = "\"" + key + "\":";
        int start = json.indexOf(search);
        if (start == -1) return "";
        start += search.length();
        char first = json.charAt(start);
        if (first == '"') {
            int end = json.indexOf('"', start + 1);
            return json.substring(start + 1, end);
        } else {
            int end = json.indexOf(',', start);
            if (end == -1) end = json.indexOf('}', start);
            return json.substring(start, end).trim();
        }
    }
}