package za.co.entelect.java_devcamp_product_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp_product_shop.dto.LoginRequestDTO;
import za.co.entelect.java_devcamp_product_shop.dto.LoginResponseDTO;
import za.co.entelect.java_devcamp_product_shop.entity.UserProfile;
import za.co.entelect.java_devcamp_product_shop.exception.InvalidCredentialsException;
import za.co.entelect.java_devcamp_product_shop.exception.UserNotFoundException;
import za.co.entelect.java_devcamp_product_shop.repository.UserProfileRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {

        // 1. Check if user exists
        UserProfile userProfile = userProfileRepository.findByUsername(request.username());
        if (userProfile == null) {
            throw new UserNotFoundException("User not found");
        }

        // 2. Check if password matches
        if (!passwordEncoder.matches(request.password(), userProfile.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        // 3. Generate token
       String token = jwtService.generateToken(userProfile.getUsername());

        // 4. Return response
        return new LoginResponseDTO(token);
    }
}
