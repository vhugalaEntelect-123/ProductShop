package za.co.entelect.java_devcamp_product_shop.exception;


import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {}