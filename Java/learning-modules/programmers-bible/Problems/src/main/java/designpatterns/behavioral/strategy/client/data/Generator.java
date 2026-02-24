package designpatterns.behavioral.strategy.client.data;


import designpatterns.behavioral.strategy.client.models.Product;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator {

    public static List<Product> generateProducts() {
        Path path = Paths.get("""
                src\\main\\resources\\designpattern\\behavioral\\strategy-product.csv""");
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .map(line -> {
                        String[] lineParts = line.split(",");
                        return new Product(lineParts[0].strip(),
                                Double.parseDouble(lineParts[1].strip()),   //price
                                Integer.parseInt(lineParts[2].strip()),     //quantity
                                Double.parseDouble(lineParts[3].strip()));  //discount
                    })
                    .toList();
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            throw new UncheckedIOException("Failed to read product file", ex);
        }
    }

}
