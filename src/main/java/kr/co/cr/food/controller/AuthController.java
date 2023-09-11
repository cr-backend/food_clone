package kr.co.cr.food.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.cr.food.dto.auth.AuthTokens;
import kr.co.cr.food.service.AuthService;
import kr.co.cr.food.utils.OauthRequestParam;
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

    @Operation(summary = "카카오 로그인", description = "auth code로 카카오 서버에 로그인을 요청합니다.", tags = { "Auth Controller" })
    @ApiResponse(code = 200, message = "요청완료", response = AuthTokens.class)
    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody OauthRequestParam params) {
        return ResponseEntity.ok(authService.login(params));
    }

    @Operation(summary = "카카오 로그인", description = "idToken을 요청 받아 검증 후 사용자를 db에 저장합니다.", tags = {"Auth Controller"})
    @ApiResponse(code = 200, message = "요청완료")
    @PostMapping("/token")
    public ResponseEntity<Long> loginKakao(@RequestBody String idToken) {
        return ResponseEntity.ok(authService.validateToken(idToken));
    }

}
