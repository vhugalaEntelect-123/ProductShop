package za.co.entelect.java_devcamp_product_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import za.co.entelect.java_devcamp_product_shop.dto.*;
import za.co.entelect.java_devcamp_product_shop.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerRequestDTO toCustomerRequest(UserProfileRequestDTO request);
    
    ProfileResponseDTO toUserProfileResponseDTO(CustomerEligibilityResponseDTO customer);
    
    @Mapping(target = "id", source = "userProfile.id")
    @Mapping(target = "username", source = "customerResponse.username")
    @Mapping(target = "firstName", source = "customerResponse.firstName")
    @Mapping(target = "lastName", source = "customerResponse.lastName")
    @Mapping(target = "idNumber", source = "customerResponse.idNumber")
    @Mapping(target = "customerTypeId", source = "customerResponse.customerTypeId")
    @Mapping(target = "customerType", ignore = true)
    @Mapping(target = "customerAccounts", ignore = true)
    ProfileResponseDTO toUserProfileResponseDTO(UserProfile userProfile, CustomerResponseDTO customerResponse);
}
