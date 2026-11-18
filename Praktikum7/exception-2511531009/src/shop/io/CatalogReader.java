package shop.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import shop.exception.CatalogLoadException;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;

public class CatalogReader {
    private static final Logger LOGGER = Logger.getLogger("shop.io.CatalogReader");
    private int skippedCount;

    public int getSkippedCount() {
        return skippedCount;
    }

    public List<Product> load(Path path) throws CatalogLoadException {
        skippedCount = 0;
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                try {
                    products.add(parseLine(trimmed));
                } catch (IllegalArgumentException e) {
                    skippedCount++;
                    LOGGER.warning("Baris " + lineNumber + " dilewati: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new CatalogLoadException("Gagal membaca file katalog: " + path, e);
        }

        return products;
    }

    private Product parseLine(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Jumlah kolom harus 4, ditemukan " + parts.length);
        }

        String sku = parts[0].trim();
        String name = parts[1].trim();
        long price = Long.parseLong(parts[2].trim());
        if (price <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0");
        }

        String detail = parts[3].trim();
        if (detail.startsWith("http://") || detail.startsWith("https://")) {
            return new DigitalProduct(sku, name, price, detail);
        }
        return new PhysicalProduct(sku, name, price, Integer.parseInt(detail));
    }
}