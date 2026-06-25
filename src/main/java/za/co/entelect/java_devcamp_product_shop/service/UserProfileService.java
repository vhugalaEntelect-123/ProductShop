package za.co.entelect.java_devcamp_product_shop.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp_product_shop.dto.*;
import za.co.entelect.java_devcamp_product_shop.entity.UserProfile;
import za.co.entelect.java_devcamp_product_shop.exception.UserAlreadyExistsException;
import za.co.entelect.java_devcamp_product_shop.mapper.CustomerMapper;
import za.co.entelect.java_devcamp_product_shop.mapper.UserProfileMapper;
import za.co.entelect.java_devcamp_product_shop.repository.UserProfileRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final CustomerService customerClient;

    public UserProfileResponseDTO registerUser(UserProfileRequestDTO request) {

        CustomerResponseDTO existingCustomer = customerService.getCustomerByEmail(request.username());
        if (existingCustomer != null) {
            throw new UserAlreadyExistsException("Customer with username " + request.username() + " already exists ");
        }
        UserProfile userProfile = userProfileMapper.toEntity(request);
        userProfile.setPassword(passwordEncoder.encode(request.password()));

        CustomerRequestDTO customerRequest = customerMapper.toCustomerRequest(request);

        customerService.createCustomer(customerRequest);
        userProfileRepository.save(userProfile);
        log.info("User profile created successfully. User ID: {}, username: {}", userProfile.getId(), userProfile.getUsername());

        return new UserProfileResponseDTO(
                userProfile.getId(),
                userProfile.getUsername(),
                "Registration successful"
        );
    }

    public ProfileResponseDTO getUserProfileById(Long customerId) {
        CustomerEligibilityResponseDTO customer = customerClient.getCustomerById(customerId);

        return customerMapper.toUserProfileResponseDTO(customer);
    }

    public CustomerEligibilityResponseDTO getUserProfileByUsername(String username) {
        log.info("Fetching user profile for username: {}", username);
        
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        if (userProfile == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        return customerClient.getCustomerById(userProfile.getId());
    }

}
