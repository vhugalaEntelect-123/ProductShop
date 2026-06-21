package za.co.entelect.java_devcamp_product_shop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDTO;
import za.co.entelect.java_devcamp_product_shop.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerGetAllTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;

    @BeforeEach
    void setUp() {
        productDTO1 = new ProductDTO(
                1L,
                "Laptop",
                "High-performance laptop",
                new BigDecimal("999.99"),
                "https://example.com/laptop.jpg",
                "DELIVERY"
        );

        productDTO2 = new ProductDTO(
                2L,
                "Mouse",
                "Wireless mouse",
                new BigDecimal("29.99"),
                "https://example.com/mouse.jpg",
                "PICKUP"
        );
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts_WhenProductsExist() {
        // Arrange
        List<ProductDTO> expectedProducts = new ArrayList<>();
        expectedProducts.add(productDTO1);
        expectedProducts.add(productDTO2);

        when(productService.getAllProducts()).thenReturn(expectedProducts);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).productId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).name()).isEqualTo("Laptop");
        assertThat(response.getBody().get(1).productId()).isEqualTo(2L);
        assertThat(response.getBody().get(1).name()).isEqualTo("Mouse");

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
        // Arrange
        List<ProductDTO> emptyProductList = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(emptyProductList);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEmpty();

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getAllProducts_ShouldReturnSingleProduct() {
        // Arrange
        List<ProductDTO> products = List.of(productDTO1);
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).productId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).name()).isEqualTo("Laptop");

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getAllProducts_ShouldReturnCorrectResponseStructure() {
        // Arrange
        List<ProductDTO> products = List.of(productDTO1);
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        
        ProductDTO productFromResponse = response.getBody().get(0);
        assertThat(productFromResponse.productId()).isEqualTo(1L);
        assertThat(productFromResponse.name()).isEqualTo("Laptop");
        assertThat(productFromResponse.price()).isEqualTo(new BigDecimal("999.99"));
        assertThat(productFromResponse.description()).isEqualTo("High-performance laptop");
    }

    @Test
    void getAllProducts_ShouldCallServiceMethodOnce() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());

        // Act
        productController.getAllProducts();

        // Assert
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getAllProducts_ShouldReturnOkStatus() {
        // Arrange
        List<ProductDTO> products = List.of(productDTO1, productDTO2);
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getAllProducts_ShouldReturnProductsWithAllFields() {
        // Arrange
        ProductDTO productWithAllFields = new ProductDTO(
                5L,
                "Monitor",
                "4K Ultra HD Monitor",
                new BigDecimal("599.99"),
                "https://example.com/monitor.jpg",
                "STANDARD"
        );
        List<ProductDTO> products = List.of(productWithAllFields);
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        
        ProductDTO product = response.getBody().get(0);
        assertThat(product.productId()).isEqualTo(5L);
        assertThat(product.name()).isEqualTo("Monitor");
        assertThat(product.description()).isEqualTo("4K Ultra HD Monitor");
        assertThat(product.price()).isEqualTo(new BigDecimal("599.99"));
        assertThat(product.imageUrl()).isEqualTo("https://example.com/monitor.jpg");
        assertThat(product.fulfilmentTypeName()).isEqualTo("STANDARD");
    }

    @Test
    void getAllProducts_ShouldHandleMultipleProducts() {
        // Arrange
        List<ProductDTO> products = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            products.add(new ProductDTO(
                    (long) i,
                    "Product " + i,
                    "Description " + i,
                    BigDecimal.valueOf(100 + i),
                    "https://example.com/product" + i + ".jpg",
                    "DELIVERY"
            ));
        }
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(5);
        for (int i = 0; i < 5; i++) {
            assertThat(response.getBody().get(i).productId()).isEqualTo((long) i + 1);
            assertThat(response.getBody().get(i).name()).isEqualTo("Product " + (i + 1));
        }

        verify(productService, times(1)).getAllProducts();
    }

}



