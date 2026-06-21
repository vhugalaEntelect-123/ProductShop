package za.co.entelect.java_devcamp_product_shop.dto;

public record UserProfileResponseDTO(
        Long id,
        String username,
        String message  // confirmation message
) {}