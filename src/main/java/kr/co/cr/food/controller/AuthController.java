package kr.co.cr.food.controller;

import kr.co.cr.food.service.AuthService;
import kr.co.cr.food.utils.OauthRequestParam;
import kr.co.cr.food.dto.auth.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody OauthRequestParam params) {
        return ResponseEntity.ok(authService.login(params));
    }

}
