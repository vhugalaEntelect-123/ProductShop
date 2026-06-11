package za.co.entelect.java_devcamp_product_shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDetailDTO;
import za.co.entelect.java_devcamp_product_shop.entity.Product;
import za.co.entelect.java_devcamp_product_shop.exception.ResourceNotFoundException;
import za.co.entelect.java_devcamp_product_shop.mapper.ProductMapper;
import za.co.entelect.java_devcamp_product_shop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products from the database");

        List<Product> products = productRepository.findAll();
        log.debug("Retrieved {} raw product entities", products.size());

        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProductDetailDTO getProductById(Long id) {
        log.info("Fetching product details for ID: {}", id);

        Product product = productRepository.findProductWithFulfilment(id);
        if (product == null) {
            log.warn("Product lookup failed for ID: {}", id);
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }

        return productMapper.toProductDetailDTO(product);
    }


    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByFulfilmentType(String fulfilmentName) {
        log.info("Searching for products with fulfilment type: {}", fulfilmentName);

        if (fulfilmentName == null || fulfilmentName.trim().isEmpty()) {
            log.error("Invalid search request: Fulfilment name parameter is empty or null");
            throw new IllegalArgumentException("Fulfilment type name cannot be empty");
        }

        List<Product> products = productRepository.findProductsByFulfilmentTypeName(fulfilmentName);
        log.debug("Found {} products matching fulfilment type: {}", products.size(), fulfilmentName);

        if (products.isEmpty()) {
            log.error("No products found for fulfilment type: {}", fulfilmentName);
            throw new ResourceNotFoundException("No products found for fulfilment type: " + fulfilmentName);
        }

        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        log.info("Saving new product record: {}", productDTO.name());

        Product productEntity = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(productEntity);

        log.info("Successfully saved product with ID: {}", savedProduct.getProductId());
        return productMapper.toDTO(savedProduct);
    }
}