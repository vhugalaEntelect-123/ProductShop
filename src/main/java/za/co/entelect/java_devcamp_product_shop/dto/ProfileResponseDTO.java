package za.co.entelect.java_devcamp_product_shop.dto;

import java.util.List;

public record ProfileResponseDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String idNumber,
        Long customerTypeId,
        CustomerTypeDTO customerType,
        List<CustomerAccountDTO> customerAccounts
) {
}
