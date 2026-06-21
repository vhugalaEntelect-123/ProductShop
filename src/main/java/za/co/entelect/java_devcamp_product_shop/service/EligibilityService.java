package za.co.entelect.java_devcamp_product_shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerAccountDTO;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerEligibilityResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.EligibilityResponseDTO;
import za.co.entelect.java_devcamp_product_shop.entity.Product;
import za.co.entelect.java_devcamp_product_shop.entity.QualifyingAccount;
import za.co.entelect.java_devcamp_product_shop.exception.ResourceNotFoundException;
import za.co.entelect.java_devcamp_product_shop.repository.ProductRepository;
import za.co.entelect.java_devcamp_product_shop.repository.QualifyingAccountRepository;
import za.co.entelect.java_devcamp_product_shop.repository.QualifyingCustomerTypeRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EligibilityService {

    private final ProductRepository productRepository;
    private final QualifyingAccountRepository qualifyingAccountRepository;
    private final QualifyingCustomerTypeRepository qualifyingCustomerTypeRepository;
    private final CustomerService customerClient;

    public EligibilityResponseDTO checkEligibility(Long productId, Long customerId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        CustomerEligibilityResponseDTO customer = customerClient.getCustomerById(customerId);

        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }

        if (customer.customerType() == null || customer.customerType().id() == null) {
            throw new IllegalStateException("Customer type is missing for customer id: " + customerId);
        }

        Long customerTypeId = customer.customerType().id();

        boolean customerTypeEligible =
                qualifyingCustomerTypeRepository.existsByProduct_ProductIdAndCustomerTypesId(
                        product.getProductId(),
                        customerTypeId
                );

        List<Long> customerAccountIds = customer.customerAccounts() == null
                ? List.of()
                : customer.customerAccounts()
                .stream()
                .map(CustomerAccountDTO::id)
                .toList();

        List<QualifyingAccount> qualifyingAccounts =
                qualifyingAccountRepository.findByProduct_ProductId(product.getProductId());

        boolean accountEligible = qualifyingAccounts.stream()
                .anyMatch(qualifyingAccount ->
                        customerAccountIds.contains(qualifyingAccount.getAccountId())
                );

        boolean eligible = customerTypeEligible && accountEligible;

        String reason;

        if (eligible) {
            reason = "Customer is eligible for this product";
        } else if (!customerTypeEligible && !accountEligible) {
            reason = "Customer type and account type do not qualify for this product";
        } else if (!customerTypeEligible) {
            reason = "Customer type does not qualify for this product";
        } else {
            reason = "Customer does not have a qualifying account for this product";
        }

        return new EligibilityResponseDTO(
                productId,
                customerId,
                eligible,
                customerTypeEligible,
                accountEligible,
                reason
        );
    }
}