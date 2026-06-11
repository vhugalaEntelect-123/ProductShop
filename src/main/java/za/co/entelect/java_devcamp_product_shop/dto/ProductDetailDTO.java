package za.co.entelect.java_devcamp_product_shop.dto;

import java.math.BigDecimal;

public record ProductDetailDTO(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        FulfilmentTypeDTO fulfilmentType
) {}