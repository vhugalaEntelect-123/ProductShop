package za.co.entelect.java_devcamp_product_shop.dto;

public record TakeUpProductRequestDTO(
        Long customerId,
        Long productId
) {
}