package za.co.entelect.java_devcamp_product_shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.entelect.java_devcamp_product_shop.dto.LoginRequestDTO;
import za.co.entelect.java_devcamp_product_shop.dto.LoginResponseDTO;
import za.co.entelect.java_devcamp_product_shop.entity.UserProfile;
import za.co.entelect.java_devcamp_product_shop.exception.InvalidCredentialsException;
import za.co.entelect.java_devcamp_product_shop.exception.UserNotFoundException;
import za.co.entelect.java_devcamp_product_shop.repository.UserProfileRepository;
import za.co.entelect.java_devcamp_product_shop.security.JwtService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;

    @Test
    void login_returnsTokenWhenCredentialsAreValid() {
        LoginRequestDTO request = new LoginRequestDTO("alice@example.com", "secret");
        UserProfile userProfile = new UserProfile(
                1L,
                "alice@example.com",
                "encoded-password",
                "Alice",
                "Smith",
                "9001015000088",
                1L
        );

        when(userProfileRepository.findByUsername("alice@example.com")).thenReturn(userProfile);
        when(passwordEncoder.matches("secret", "encoded-password")).thenReturn(true);
        when(jwtService.generateToken("alice@example.com")).thenReturn("jwt-token");

        LoginResponseDTO response = loginService.login(request);

        assertThat(response.token()).isEqualTo("jwt-token");
        verify(userProfileRepository).findByUsername("alice@example.com");
        verify(passwordEncoder).matches("secret", "encoded-password");
        verify(jwtService).generateToken("alice@example.com");
    }

    @Test
    void login_throwsWhenUserDoesNotExist() {
        LoginRequestDTO request = new LoginRequestDTO("missing@example.com", "secret");
        when(userProfileRepository.findByUsername("missing@example.com")).thenReturn(null);

        assertThatThrownBy(() -> loginService.login(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        verify(userProfileRepository).findByUsername("missing@example.com");
        verifyNoInteractions(passwordEncoder, jwtService);
    }
}
