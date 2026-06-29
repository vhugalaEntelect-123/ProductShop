package za.co.entelect.java_devcamp_product_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.entelect.java_devcamp_product_shop.dto.TakeUpProductResponseDTO;
import za.co.entelect.java_devcamp_product_shop.service.TakeUpProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class TakeUpProductController {

    private final TakeUpProductService takeUpProductService;


    @PostMapping("/{productId}/take-up")
    public ResponseEntity<TakeUpProductResponseDTO> takeUpProduct(
            @PathVariable Long productId,
            Authentication authentication
    ) {
        String username = authentication.getName();

        TakeUpProductResponseDTO response =
                takeUpProductService.takeUpProduct(productId, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}