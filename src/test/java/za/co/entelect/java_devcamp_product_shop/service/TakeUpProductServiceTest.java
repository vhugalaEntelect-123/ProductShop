package za.co.entelect.java_devcamp_product_shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.EligibilityResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.TakeUpProductResponseDTO;
import za.co.entelect.java_devcamp_product_shop.entity.Order;
import za.co.entelect.java_devcamp_product_shop.exception.CustomerNotEligibleException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TakeUpProductServiceTest {

    @Mock
    private EligibilityService eligibilityService;

    @Mock
    private OrderService orderService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private TakeUpProductService takeUpProductService;

    @Test
    void takeUpProduct_returnsResponseWhenCustomerIsEligible() {
        CustomerResponseDTO customer = new CustomerResponseDTO(
                11L,
                "alice@example.com",
                "Alice",
                "Smith",
                1L,
                "9001015000088"
        );
        EligibilityResponseDTO eligibility = new EligibilityResponseDTO(
                100L,
                11L,
                true,
                true,
                true,
                "Customer is eligible for this product"
        );
        Order order = new Order();
        order.setOrderId(55L);
        order.setStatus("PENDING");

        when(customerService.getCustomerByEmail("alice@example.com")).thenReturn(customer);
        when(eligibilityService.checkEligibility(100L, 11L)).thenReturn(eligibility);
        when(orderService.createPendingOrder(11L, 100L)).thenReturn(order);

        TakeUpProductResponseDTO response = takeUpProductService.takeUpProduct(100L, "alice@example.com");

        assertThat(response.orderId()).isEqualTo(55L);
        assertThat(response.customerId()).isEqualTo(11L);
        assertThat(response.productId()).isEqualTo(100L);
        assertThat(response.status()).isEqualTo("PENDING");
        verify(customerService).getCustomerByEmail("alice@example.com");
        verify(eligibilityService).checkEligibility(100L, 11L);
        verify(orderService).createPendingOrder(11L, 100L);
    }

    @Test
    void takeUpProduct_throwsWhenCustomerIsNotEligible() {
        CustomerResponseDTO customer = new CustomerResponseDTO(
                11L,
                "alice@example.com",
                "Alice",
                "Smith",
                1L,
                "9001015000088"
        );
        EligibilityResponseDTO eligibility = new EligibilityResponseDTO(
                100L,
                11L,
                false,
                false,
                false,
                "Customer type does not qualify for this product"
        );

        when(customerService.getCustomerByEmail("alice@example.com")).thenReturn(customer);
        when(eligibilityService.checkEligibility(100L, 11L)).thenReturn(eligibility);

        assertThatThrownBy(() -> takeUpProductService.takeUpProduct(100L, "alice@example.com"))
                .isInstanceOf(CustomerNotEligibleException.class)
                .hasMessage("Customer type does not qualify for this product");

        verify(customerService).getCustomerByEmail("alice@example.com");
        verify(eligibilityService).checkEligibility(100L, 11L);
        verify(orderService, never()).createPendingOrder(11L, 100L);
    }
}
