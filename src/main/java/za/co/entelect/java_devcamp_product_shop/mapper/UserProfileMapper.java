package za.co.entelect.java_devcamp_product_shop.mapper;

import org.mapstruct.Mapper;
import za.co.entelect.java_devcamp_product_shop.dto.UserProfileRequestDTO;
import za.co.entelect.java_devcamp_product_shop.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile toEntity(UserProfileRequestDTO dto);

}