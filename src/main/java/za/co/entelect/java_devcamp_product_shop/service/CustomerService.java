package za.co.entelect.java_devcamp_product_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerRequestDTO;
import za.co.entelect.java_devcamp_product_shop.dto.CustomerResponseDTO;
import za.co.entelect.java_devcamp_product_shop.exception.CustomerServiceException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestClient customerRestClient;
    private final TokenService tokenService;

    public void createCustomer(CustomerRequestDTO customerRequest) {
        String token = tokenService.fetchToken();

        try {
            customerRestClient.post()
                    .uri("/v1/customer/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(customerRequest)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception e) {
            throw new CustomerServiceException("Failed to register customer", e);
        }
    }

    public CustomerResponseDTO getCustomerByEmail(String emailAddress) {
        String token = tokenService.fetchToken();

        try {
            return customerRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/customer")
                            .queryParam("emailAddress", emailAddress)
                            .build()
                    )
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(CustomerResponseDTO.class);

        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }  catch (Exception e) {
            throw new CustomerServiceException("Failed to fetch customer: " + emailAddress, e);
        }
    }
}