package za.co.entelect.java_devcamp_product_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDTO;
import za.co.entelect.java_devcamp_product_shop.dto.ProductDetailDTO;
import za.co.entelect.java_devcamp_product_shop.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getProductById(@PathVariable Long id) {
        ProductDetailDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getProductsByFulfilmentType(
            @RequestParam("fulfilmentType") String fulfilmentType) {
        List<ProductDTO> products = productService.getProductsByFulfilmentType(fulfilmentType);
        return ResponseEntity.ok(products);
    }


    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}
