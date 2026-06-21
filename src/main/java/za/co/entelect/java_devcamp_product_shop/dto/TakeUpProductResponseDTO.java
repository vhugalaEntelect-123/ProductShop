package za.co.entelect.java_devcamp_product_shop.dto;

public record TakeUpProductResponseDTO(
        Long orderId,
        Long customerId,
        Long productId,
        String status,
        String message
) {
}
