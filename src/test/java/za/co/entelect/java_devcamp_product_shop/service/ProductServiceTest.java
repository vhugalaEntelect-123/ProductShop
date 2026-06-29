package za.co.entelect.java_devcamp_product_shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.entelect.java_devcamp_product_shop.dto.FulfilmentTypeDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDetailDTO;
import za.co.entelect.java_devcamp_product_shop.entity.FulfilmentType;
import za.co.entelect.java_devcamp_product_shop.entity.Product;
import za.co.entelect.java_devcamp_product_shop.exception.ResourceNotFoundException;
import za.co.entelect.java_devcamp_product_shop.mapper.ProductMapper;
import za.co.entelect.java_devcamp_product_shop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProductById_returnsMappedProductWhenFound() {
        Product product = new Product();
        product.setProductId(1L);
        product.setName("Laptop");
        product.setDescription("High-performance laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setImageUrl("https://example.com/laptop.jpg");
        product.setFulfilmentType(new FulfilmentType());
        product.getFulfilmentType().setName("DELIVERY");

        ProductDetailDTO expected = new ProductDetailDTO(
                1L,
                "Laptop",
                "High-performance laptop",
                new BigDecimal("999.99"),
                "https://example.com/laptop.jpg",
                new FulfilmentTypeDTO("DELIVERY", "Delivery")
        );

        when(productRepository.findByProductId(1L)).thenReturn(product);
        when(productMapper.toProductDetailDTO(product)).thenReturn(expected);

        ProductDetailDTO actual = productService.getProductById(1L);

        assertThat(actual).isEqualTo(expected);
        verify(productRepository).findByProductId(1L);
        verify(productMapper).toProductDetailDTO(product);
    }

    @Test
    void getProductsByFulfilmentType_throwsWhenNothingMatches() {
        when(productRepository.findByFulfilmentTypeNameIgnoreCase("DELIVERY")).thenReturn(List.of());

        assertThatThrownBy(() -> productService.getProductsByFulfilmentType("DELIVERY"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No products found for fulfilment type: DELIVERY");

        verify(productRepository).findByFulfilmentTypeNameIgnoreCase("DELIVERY");
        verify(productMapper, never()).toDTO(any(Product.class));
    }
}
