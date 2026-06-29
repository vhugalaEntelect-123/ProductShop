package za.co.entelect.java_devcamp_product_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.java_devcamp_product_shop.dto.*;
import za.co.entelect.java_devcamp_product_shop.service.LoginService;
import za.co.entelect.java_devcamp_product_shop.service.UserProfileService;

@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<UserProfileResponseDTO> register(@RequestBody UserProfileRequestDTO request) {
        UserProfileResponseDTO response = userProfileService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = loginService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerEligibilityResponseDTO> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CustomerEligibilityResponseDTO response = userProfileService.getUserProfileByUsername(username);
        return ResponseEntity.ok(response);
    }


}