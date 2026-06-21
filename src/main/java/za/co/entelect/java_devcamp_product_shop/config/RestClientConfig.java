package za.co.entelect.java_devcamp_product_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Bean
    public RestClient customerRestClient() {
        return RestClient.builder()
                .baseUrl(customerServiceUrl)
                .build();
    }

    @Bean
    public RestClient authRestClient() {
        return RestClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }
}
