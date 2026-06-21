package za.co.entelect.java_devcamp_product_shop.dto;

public record UserProfileRequestDTO(
        String username,
        String firstName,
        String lastName,
        String idNumber,
        Long customerTypeId,
        String password
) {}
