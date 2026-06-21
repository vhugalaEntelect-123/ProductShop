package za.co.entelect.java_devcamp_product_shop.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import za.co.entelect.java_devcamp_product_shop.dto.FulfilmentTypeDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDetailDTO;
import za.co.entelect.java_devcamp_product_shop.service.ProductService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerGetByIdTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDetailDTO productDetail;
    private FulfilmentTypeDTO fulfilmentType;

    @BeforeEach
    void setUp() {
        fulfilmentType = new FulfilmentTypeDTO(
                "DELIVERY",
                "Standard delivery to address"
        );

        productDetail = new ProductDetailDTO(
                1L,
                "Laptop Pro",
                "High-performance laptop with 16GB RAM",
                new BigDecimal("1299.99"),
                "https://example.com/laptop-pro.jpg",
                fulfilmentType
        );
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo(1L);
        assertThat(response.getBody().name()).isEqualTo("Laptop Pro");
        assertThat(response.getBody().description()).isEqualTo("High-performance laptop with 16GB RAM");
        assertThat(response.getBody().price()).isEqualTo(new BigDecimal("1299.99"));

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void getProductById_ShouldReturnProductWithFulfilmentDetails() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().fulfilmentType()).isNotNull();
        assertThat(response.getBody().fulfilmentType().name()).isEqualTo("DELIVERY");
        assertThat(response.getBody().fulfilmentType().description()).isEqualTo("Standard delivery to address");
    }

    @Test
    void getProductById_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        // Arrange
        Long nonExistentProductId = 999L;
        when(productService.getProductById(nonExistentProductId))
                .thenThrow(new EntityNotFoundException("Product not found with ID: " + nonExistentProductId));

        // Act & Assert
        assertThatThrownBy(() -> productController.getProductById(nonExistentProductId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product not found with ID: 999");

        verify(productService, times(1)).getProductById(nonExistentProductId);
    }

    @Test
    void getProductById_ShouldReturnCorrectProductId() {
        // Arrange
        Long productId = 5L;
        ProductDetailDTO product = new ProductDetailDTO(
                5L,
                "Mouse",
                "Wireless mouse",
                new BigDecimal("25.99"),
                "https://example.com/mouse.jpg",
                new FulfilmentTypeDTO("PICKUP", "Pick up from store")
        );
        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getBody().productId()).isEqualTo(5L);
        assertThat(response.getBody().productId()).isNotEqualTo(1L);
    }

    @Test
    void getProductById_ShouldReturnCorrectPrice() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getBody().price()).isEqualTo(new BigDecimal("1299.99"));
        assertThat(response.getBody().price()).isGreaterThan(new BigDecimal("1000"));
    }

    @Test
    void getProductById_ShouldReturnImageUrl() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getBody().imageUrl()).isNotNull();
        assertThat(response.getBody().imageUrl()).isEqualTo("https://example.com/laptop-pro.jpg");
        assertThat(response.getBody().imageUrl()).contains("example.com");
    }

    @Test
    void getProductById_ShouldCallServiceWithCorrectId() {
        // Arrange
        Long productId = 42L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        productController.getProductById(productId);

        // Assert
        verify(productService, times(1)).getProductById(42L);
    }

    @Test
    void getProductById_ShouldReturnOkStatus() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getProductById_ShouldNotReturnNull() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getProductById_ShouldReturnAllProductFields() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        ProductDetailDTO product = response.getBody();
        assertThat(product.productId()).isNotNull();
        assertThat(product.name()).isNotNull();
        assertThat(product.description()).isNotNull();
        assertThat(product.price()).isNotNull();
        assertThat(product.imageUrl()).isNotNull();
        assertThat(product.fulfilmentType()).isNotNull();
    }

    @Test
    void getProductById_ShouldHandleZeroId() {
        // Arrange
        Long productId = 0L;
        when(productService.getProductById(productId))
                .thenThrow(new EntityNotFoundException("Product not found with ID: " + productId));

        // Act & Assert
        assertThatThrownBy(() -> productController.getProductById(productId))
                .isInstanceOf(EntityNotFoundException.class);

        verify(productService, times(1)).getProductById(0L);
    }

    @Test
    void getProductById_ShouldHandleNegativeId() {
        // Arrange
        Long productId = -1L;
        when(productService.getProductById(productId))
                .thenThrow(new EntityNotFoundException("Product not found with ID: " + productId));

        // Act & Assert
        assertThatThrownBy(() -> productController.getProductById(productId))
                .isInstanceOf(EntityNotFoundException.class);

        verify(productService, times(1)).getProductById(-1L);
    }

    @Test
    void getProductById_ShouldReturnCorrectDescription() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getBody().description()).isEqualTo("High-performance laptop with 16GB RAM");
        assertThat(response.getBody().description()).contains("laptop");
    }

    @Test
    void getProductById_ShouldReturnPickupFulfilmentType() {
        // Arrange
        Long productId = 2L;
        FulfilmentTypeDTO pickupType = new FulfilmentTypeDTO(
                "PICKUP",
                "Pick up from nearest store"
        );
        ProductDetailDTO pickupProduct = new ProductDetailDTO(
                2L,
                "Monitor",
                "4K Monitor",
                new BigDecimal("399.99"),
                "https://example.com/monitor.jpg",
                pickupType
        );
        when(productService.getProductById(productId)).thenReturn(pickupProduct);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getBody().fulfilmentType().name()).isEqualTo("PICKUP");
        assertThat(response.getBody().fulfilmentType().description()).contains("store");
    }

    @Test
    void getProductById_ShouldReturnDifferentProductsForDifferentIds() {
        // Arrange
        Long productId1 = 1L;
        Long productId2 = 2L;
        
        ProductDetailDTO product1 = new ProductDetailDTO(
                1L, "Laptop", "Desc1", new BigDecimal("1000"), "url1",
                new FulfilmentTypeDTO("DELIVERY", "desc1")
        );
        
        ProductDetailDTO product2 = new ProductDetailDTO(
                2L, "Mouse", "Desc2", new BigDecimal("50"), "url2",
                new FulfilmentTypeDTO("PICKUP", "desc2")
        );
        
        when(productService.getProductById(productId1)).thenReturn(product1);
        when(productService.getProductById(productId2)).thenReturn(product2);

        // Act
        ResponseEntity<ProductDetailDTO> response1 = productController.getProductById(productId1);
        ResponseEntity<ProductDetailDTO> response2 = productController.getProductById(productId2);

        // Assert
        assertThat(response1.getBody().name()).isEqualTo("Laptop");
        assertThat(response2.getBody().name()).isEqualTo("Mouse");
        assertThat(response1.getBody().price()).isNotEqualTo(response2.getBody().price());
    }

    @Test
    void getProductById_ShouldHandleLargeProductId() {
        // Arrange
        Long largeProductId = 9999999L;
        when(productService.getProductById(largeProductId)).thenReturn(productDetail);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(largeProductId);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(productService, times(1)).getProductById(largeProductId);
    }

    @Test
    void getProductById_ShouldVerifyServiceCalledExactlyOnce() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(productDetail);

        // Act
        productController.getProductById(productId);
        productController.getProductById(productId);

        // Assert - Verify was called exactly 2 times (once for each call above)
        verify(productService, times(2)).getProductById(productId);
    }

    @Test
    void getProductById_ShouldPreserveDecimalPrecision() {
        // Arrange
        Long productId = 1L;
        ProductDetailDTO precisionProduct = new ProductDetailDTO(
                1L,
                "Product",
                "Description",
                new BigDecimal("99.99"),
                "https://example.com/product.jpg",
                fulfilmentType
        );
        when(productService.getProductById(productId)).thenReturn(precisionProduct);

        // Act
        ResponseEntity<ProductDetailDTO> response = productController.getProductById(productId);

        // Assert
        assertThat(response.getBody().price()).isEqualTo(new BigDecimal("99.99"));
        assertThat(response.getBody().price().scale()).isEqualTo(2);
    }

}
