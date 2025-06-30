package com.gab.products_api.service;

import com.gab.products_api.domain.model.Product;
import com.gab.products_api.domain.request.ProductRequest;
import com.gab.products_api.domain.response.ProductResponse;
import com.gab.products_api.repository.ProductRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResponse> getProducts() {
        return productListToResponseList(productRepository.findAll());
    }

    public ProductResponse getById(UUID id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NotFoundException("product not found");
        }

        return productToResponse(product.get());
    }

    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = this.productRepository.save(requestToProduct(productRequest));

        return productToResponse(product);
    }

    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Optional<Product> productOptional = this.productRepository.findById(UUID.fromString(id));

        if (productOptional.isEmpty()) {
            throw new NotFoundException("product not found");
        }

        Product product = productOptional.get();

        if (productRequest.name() != null) {
            product.setName(productRequest.name());
        }

        if (productRequest.description() != null) {
            product.setDescription(productRequest.description());
        }

        if (productRequest.price() != null) {
            product.setPrice(productRequest.price());
        }

        if (productRequest.stock() != null) {
            product.setStock(productRequest.stock());
        }

        return productToResponse(this.productRepository.save(product));
    }

    public void deleteProduct(UUID id) {
        this.productRepository.deleteById(id);
    }

    private List<ProductResponse> productListToResponseList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new ArrayList<>();
        }

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : products) {
            productResponseList.add(productToResponse(product));
        }

        return productResponseList;
    }

    private ProductResponse productToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock()
        );
    }

    private Product requestToProduct(ProductRequest productRequest) {
        Product product = new Product();

        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStock(productRequest.stock());

        return product;
    }
}
