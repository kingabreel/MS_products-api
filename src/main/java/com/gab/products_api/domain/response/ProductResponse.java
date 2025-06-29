package com.gab.products_api.domain.response;

import java.util.UUID;

public record ProductResponse(UUID id, String name, double price, String description, int stock) {
}
