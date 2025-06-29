package com.gab.products_api.domain.request;

public record ProductRequest (String name, String description, int stock, double price) {
}
