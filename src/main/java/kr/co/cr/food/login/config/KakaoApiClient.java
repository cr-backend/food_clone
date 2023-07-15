package kr.co.cr.food.login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
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

    private final RestTemplate restTemplate;

    public String requestAccessToken(OauthRequestParam params){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUrl);

        HttpEntity request = new HttpEntity(body, httpHeaders);

        OauthTokens response = restTemplate.postForObject(tokenUrl, request, OauthTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    public OauthInfoResponse requestOauthInfo(String accessToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity request = new HttpEntity(body, httpHeaders);

        return restTemplate.postForObject(userInfoUrl, request, OauthInfoResponse.class);
    }



}
