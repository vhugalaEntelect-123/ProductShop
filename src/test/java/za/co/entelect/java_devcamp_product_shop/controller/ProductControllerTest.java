package za.co.entelect.java_devcamp_product_shop.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import za.co.entelect.java_devcamp_product_shop.dto.FulfilmentTypeDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDetailDTO;
import za.co.entelect.java_devcamp_product_shop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProducts_returnsProductsFromService() {
        List<ProductDTO> products = List.of(
                new ProductDTO(1L, "Laptop", "High-performance laptop", new BigDecimal("999.99"), "url", "DELIVERY")
        );
        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(products);
        verify(productService).getAllProducts();
    }

    @Test
    void getProductById_returnsProductFromService() {
        ProductDetailDTO product = new ProductDetailDTO(
                1L,
                "Laptop",
                "High-performance laptop",
                new BigDecimal("999.99"),
                "url",
                new FulfilmentTypeDTO("DELIVERY", "Delivery")
        );
        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<ProductDetailDTO> response = productController.getProductById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService).getProductById(1L);
    }
}
