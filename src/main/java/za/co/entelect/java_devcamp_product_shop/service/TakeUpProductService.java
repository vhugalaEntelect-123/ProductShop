package za.co.entelect.java_devcamp_product_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    @Transactional
    public TakeUpProductResponseDTO takeUpProduct(Long productId, Long customerId) {

        log.info("Take up product requested. productId={}, customerId={}", productId, customerId);

        EligibilityResponseDTO eligibility =
                eligibilityService.checkEligibility(productId, customerId);

        if (!eligibility.eligible()) {
            throw new CustomerNotEligibleException(eligibility.reason());
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setContractUrl(null);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        order.getOrderItems().add(orderItem);

        Order savedOrder = orderRepository.save(order);

        return new TakeUpProductResponseDTO(
                savedOrder.getOrderId(),
                customerId,
                productId,
                "PENDING",
                "Product take-up request created successfully"
        );
    }
}
