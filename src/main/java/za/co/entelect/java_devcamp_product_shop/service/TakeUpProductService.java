package za.co.entelect.java_devcamp_product_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.EligibilityResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.TakeUpProductResponseDTO;
import za.co.entelect.java_devcamp_product_shop.entity.Order;
import za.co.entelect.java_devcamp_product_shop.entity.OrderItem;
import za.co.entelect.java_devcamp_product_shop.entity.Product;
import za.co.entelect.java_devcamp_product_shop.exception.CustomerNotEligibleException;
import za.co.entelect.java_devcamp_product_shop.exception.ResourceNotFoundException;
import za.co.entelect.java_devcamp_product_shop.repository.OrderItemRepository;
import za.co.entelect.java_devcamp_product_shop.repository.OrderRepository;
import za.co.entelect.java_devcamp_product_shop.repository.ProductRepository;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class TakeUpProductService {

    private final EligibilityService eligibilityService;
    private final OrderService orderService;
    private final CustomerService customerService;

    @Transactional
    public TakeUpProductResponseDTO takeUpProduct(Long productId, String emailAddress) {

        CustomerResponseDTO customerResponse = customerService.getCustomerByEmail(emailAddress);
        Long customerId = customerResponse.id();

        log.info("Take up product requested. productId={}, customerId={}", productId, customerId);

        EligibilityResponseDTO eligibility =
                eligibilityService.checkEligibility(productId, customerId);

        if (!eligibility.eligible()) {
            log.warn(
                    "Take up product rejected. customerId={}, productId={}, reason={}",
                    customerId,
                    productId,
                    eligibility.reason()
            );

            throw new CustomerNotEligibleException(eligibility.reason());
        }

        Order savedOrder = orderService.createPendingOrder(customerId, productId);

        return new TakeUpProductResponseDTO(
                savedOrder.getOrderId(),
                customerId,
                productId,
                savedOrder.getStatus(),
                "Product take-up request created successfully"
        );
    }
}
