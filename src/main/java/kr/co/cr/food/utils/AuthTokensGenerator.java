package kr.co.cr.food.utils;

import kr.co.cr.food.dto.auth.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;

    public AuthTokens generate(Long memberId){
        long now = (new Date()).getTime();
        Date expiredAccessToken = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date expiredRefreshToken = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String sub = memberId.toString();
        String accessToken = jwtTokenProvider.generate(sub, expiredAccessToken);
        String refreshToken = jwtTokenProvider.generate(sub, expiredRefreshToken);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenProvider.extractSub(accessToken));
    }

}
