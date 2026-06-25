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


        UserProfile userProfile = userProfileRepository.findByUsername(request.username());
        if (userProfile == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(request.password(), userProfile.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

       String token = jwtService.generateToken(userProfile.getUsername());

        return new LoginResponseDTO(token);
    }
}
