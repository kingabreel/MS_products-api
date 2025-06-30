package com.gab.products_api.domain.request;

public record ProductRequest (String name, String description, Integer stock, Double price) {
}
