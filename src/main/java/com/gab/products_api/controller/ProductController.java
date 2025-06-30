package com.gab.products_api.controller;

import com.gab.products_api.domain.request.ProductRequest;
import com.gab.products_api.domain.response.ProductResponse;
import com.gab.products_api.domain.response.Response;
import com.gab.products_api.service.ProductService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductResponse>>> getProducts() {
        try {
            List<ProductResponse> products = productService.getProducts();

            Response<List<ProductResponse>> response = new Response<>(products, "products returned");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductResponse>> getById(@PathVariable UUID id) {
        try {
            ProductResponse product = productService.getById(id);

            Response<ProductResponse> response = new Response<>(product, "product returned");

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            ProductResponse product = productService.addProduct(productRequest);

            Response<ProductResponse> response = new Response<>(product, "product created");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductResponse>> updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse product = productService.updateProduct(id, productRequest);

            Response<ProductResponse> response = new Response<>(product, "product created");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@RequestBody UUID id) {
        try {
            productService.deleteProduct(id);

            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
