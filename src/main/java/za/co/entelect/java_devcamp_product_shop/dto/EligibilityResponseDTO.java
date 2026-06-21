package za.co.entelect.java_devcamp_product_shop.dto;

public record EligibilityResponseDTO(
        Long productId,
        Long customerId,
        boolean eligible,
        boolean customerTypeEligible,
        boolean accountEligible,
        String reason
) {
}
