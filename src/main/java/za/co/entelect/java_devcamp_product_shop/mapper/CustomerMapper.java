package za.co.entelect.java_devcamp_product_shop.mapper;

import org.mapstruct.Mapper;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerRequestDTO;
import za.co.entelect.java_devcamp_product_shop.dto.UserProfileRequestDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequestDTO toCustomerRequest(UserProfileRequestDTO request);
}
