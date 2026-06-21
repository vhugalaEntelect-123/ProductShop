package za.co.entelect.java_devcamp_product_shop.service;

import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import za.co.entelect.java_devcamp_product_shop.dto.TokenResponseDTO;

import java.util.Base64;
@Service
//@RequiredArgsConstructor
public class TokenService {

    @Qualifier("authRestClient")
    private final RestClient authRestClient;

    @Value("${auth.service.username}")
    private String username;

    @Value("${auth.service.password}")
    private String password;

    public TokenService(@Qualifier("authRestClient") RestClient authRestClient) {
        this.authRestClient = authRestClient;
    }

    public String fetchToken() {


        try {
            String rawToken = authRestClient.post()
                    .uri("/token")
                    .contentType(MediaType.APPLICATION_JSON) // Try adding this context hint
                    .headers(headers -> headers.setBasicAuth(username, password))
                    .retrieve()
                    .body(String.class);


            return rawToken;

        } catch (HttpClientErrorException.Unauthorized e) {
            System.err.println("CRITICAL: The auth server explicitly rejected these credentials.");
            throw e;
        }
    }
}
