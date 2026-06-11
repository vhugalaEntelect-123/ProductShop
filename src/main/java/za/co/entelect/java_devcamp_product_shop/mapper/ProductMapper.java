package za.co.entelect.java_devcamp_product_shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import za.co.entelect.java_devcamp_product_shop.dto.FulfilmentTypeDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDetailDTO;
import za.co.entelect.java_devcamp_product_shop.entity.FulfilmentType;
import za.co.entelect.java_devcamp_product_shop.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    //Maps a Product Entity to a ProductDTO.
    @Mapping(target = "fulfilmentTypeName", source = "fulfilmentType.name")
    ProductDTO toDTO(Product product);

    //Maps a ProductDTO back into a Product Entity.
    @Mapping(target = "fulfilmentType.name", source = "fulfilmentTypeName")
    Product toEntity(ProductDTO productDTO);

    ProductDetailDTO toProductDetailDTO(Product product);

    FulfilmentTypeDTO toDTO(FulfilmentType fulfilmentType);
}