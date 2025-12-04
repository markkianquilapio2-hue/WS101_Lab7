package com.Quilapio.lab7.controller;

import com.Quilapio.lab7.model.Product;
import com.Quilapio.lab7.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Product resources.
 * Handles CRUD operations via HTTP requests.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Dependency Injection: Spring automatically injects the ProductService bean
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /products
     * Retrieves all products.
     * @return A list of products with HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * GET /products/{id}
     * Retrieves a single product by ID.
     * @param id The ID of the product.
     * @return The product with HTTP 200 OK, or HTTP 404 NOT FOUND if the product doesn't exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok) // If found, return 200 OK with the product
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 NOT FOUND
    }

    /**
     * POST /products
     * Creates a new product.
     * @param product The product object from the request body.
     * @return The created product with HTTP 201 CREATED status.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    /**
     * PUT /products/{id}
     * Updates an existing product.
     * @param id The ID of the product to update.
     * @param updatedProduct The updated product data from the request body.
     * @return The updated product with HTTP 200 OK, or HTTP 404 NOT FOUND if the product doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.update(id, updatedProduct)
                .map(ResponseEntity::ok) // If updated, return 200 OK with the updated product
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 NOT FOUND
    }

    /**
     * DELETE /products/{id}
     * Deletes a product by ID.
     * @param id The ID of the product to delete.
     * @return HTTP 204 NO CONTENT on successful deletion, or HTTP 404 NOT FOUND if the product wasn't found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteById(id);
        if (isDeleted) {
            // 204 No Content is standard for successful deletions without a response body
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // 404 if the resource to delete doesn't exist
        }
    }
}
