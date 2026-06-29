package za.co.entelect.java_devcamp_product_shop.dto;

public record CustomerResponseDTO(
        long id,
        String username,
        String firstName,
        String lastName,
        Long customerTypeId,
        String idNumber
) {}
