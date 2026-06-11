package za.co.entelect.java_devcamp_product_shop.dto;


import java.math.BigDecimal;

public record ProductDTO(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        String fulfilmentTypeName
) {}
