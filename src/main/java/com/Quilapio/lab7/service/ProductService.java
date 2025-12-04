package com.Quilapio.lab7.service;

import com.Quilapio.lab7.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service layer to handle business logic and data access for Product entities.
 * This class uses an in-memory Map to simulate a database.
 */
@Service
public class ProductService {

    // Simulates a database table using a concurrent hash map for thread safety
    private final Map<Long, Product> productRepository = new ConcurrentHashMap<>();

    // Used to generate unique IDs for new products
    private final AtomicLong idGenerator = new AtomicLong(3);

    // Initialize with some mock data
    public ProductService() {
        productRepository.put(1L, new Product(1L, "Laptop Pro X", 1299.99));
        productRepository.put(2L, new Product(2L, "Wireless Mouse", 25.50));
        productRepository.put(3L, new Product(3L, "Mechanical Keyboard", 89.99));
    }

    /**
     * Retrieves all products.
     * @return A list of all products.
     */
    public List<Product> findAll() {
        // Return a new ArrayList to prevent external modifications to the internal map
        return new ArrayList<>(productRepository.values());
    }

    /**
     * Retrieves a product by its ID.
     * @param id The ID of the product.
     * @return An Optional containing the product if found, or empty otherwise.
     */
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productRepository.get(id));
    }

    /**
     * Creates a new product and assigns it a unique ID.
     * @param product The product object to save.
     * @return The saved product with the generated ID.
     */
    public Product save(Product product) {
        // Assign a new ID only if the product ID is null
        if (product.getId() == null || product.getId() == 0) {
            product.setId(idGenerator.incrementAndGet());
        }
        productRepository.put(product.getId(), product);
        return product;
    }

    /**
     * Updates an existing product.
     * @param id The ID of the product to update.
     * @param updatedProduct The product data to update with.
     * @return An Optional containing the updated product if the ID was found, or empty otherwise.
     */
    public Optional<Product> update(Long id, Product updatedProduct) {
        if (productRepository.containsKey(id)) {
            // Ensure the ID of the updated object matches the path ID
            updatedProduct.setId(id);
            productRepository.put(id, updatedProduct);
            return Optional.of(updatedProduct);
        }
        return Optional.empty();
    }

    /**
     * Deletes a product by its ID.
     * @param id The ID of the product to delete.
     * @return true if the product was successfully deleted, false otherwise.
     */
    public boolean deleteById(Long id) {
        return productRepository.remove(id) != null;
    }
}
