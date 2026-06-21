package za.co.entelect.java_devcamp_product_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.entelect.java_devcamp_product_shop.dto.LoginRequestDTO;
import za.co.entelect.java_devcamp_product_shop.dto.LoginResponseDTO;
import za.co.entelect.java_devcamp_product_shop.dto.UserProfileRequestDTO;
import za.co.entelect.java_devcamp_product_shop.dto.UserProfileResponseDTO;
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
}