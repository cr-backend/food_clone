package kr.co.cr.food.utils;

import kr.co.cr.food.dto.auth.PublicKeyResponse;
import kr.co.cr.food.exception.NotValidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth2.kakao.provider.kakao.token-uri}")
    private String tokenUrl;
    @Value("${oauth2.kakao.provider.kakao.user-info-uri}")
    private String userInfoUrl;
    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    @Value("${oauth2.kakao.client-secret}")
    private String clientSecret;
    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUrl;

    @Value("${oauth.uri.public-key-uri}")
    private String keyUrl;

    private final RestTemplate restTemplate;

//    public String requestAccessToken(OauthRequestParam params) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> body = params.makeBody();
//        body.add("grant_type", GRANT_TYPE);
//        body.add("client_id", clientId);
//        body.add("client_secret", clientSecret);
//        body.add("redirect_uri", redirectUrl);
//
//        HttpEntity request = new HttpEntity(body, httpHeaders);
//
//        if (request == null)
//            throw new HttpClientErrorException("Kakao 토큰을 요청할 수 없습니다.");
//
//        OauthTokens response = restTemplate.postForObject(tokenUrl, request, OauthTokens.class);
//        log.info("idToken ={}", response.getIdToken());
//
//        if (response == null)
//            throw new HttpClientErrorException("Kakao 토큰을 발급 받을 수 없습니다.");
//
//        return response.getAccessToken();
//    }
//
//    public OauthInfoResponse requestOauthInfo(String accessToken) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.set("Authorization", "Bearer " + accessToken);
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");
//
//        HttpEntity request = new HttpEntity(body, httpHeaders);
//
//        if (request == null)
//            throw new HttpClientErrorException("사용자 정보를 Kakao Auth Server로부터 요청할 수 없습니다.");
//
//        OauthInfoResponse response = restTemplate.postForObject(userInfoUrl, request, OauthInfoResponse.class);
//        return response;
//    }

    /**
     * 사용하는 메소드
     */
    public void headerKidInfo(Map<String, Object> header) {

        String kid = header.get("kid").toString();

        PublicKeyResponse keyResponse = restTemplate.getForObject(keyUrl, PublicKeyResponse.class);

        keyResponse.getKeys().stream()
                .filter(o -> o.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new NotValidValueException("유효하지 않은 kid 값입니다."));
    }


}
