package za.co.entelect.java_devcamp_product_shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.entelect.java_devcamp_product_shop.dto.TakeUpProductResponseDTO;
import za.co.entelect.java_devcamp_product_shop.service.TakeUpProductService;

@RestController
@RequestMapping("/api/products")
public class TakeUpProductController {

    private final TakeUpProductService takeUpProductService;

    public TakeUpProductController(TakeUpProductService takeUpProductService) {
        this.takeUpProductService = takeUpProductService;
    }

    @PostMapping("/{productId}/take-up/{customerId}")
    public ResponseEntity<TakeUpProductResponseDTO> takeUpProduct(
            @PathVariable Long productId,
            @PathVariable Long customerId
    ) {
        TakeUpProductResponseDTO response =
                takeUpProductService.takeUpProduct(productId, customerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}