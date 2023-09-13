package kr.co.cr.food.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kr.co.cr.food.dto.auth.OauthInfoResponse;
import kr.co.cr.food.dto.auth.OauthTokens;
import kr.co.cr.food.dto.auth.PublicKeyResponse;
import kr.co.cr.food.exception.HttpClientErrorException;
import kr.co.cr.food.exception.NotValidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

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
    private JsonParser jsonParser = new BasicJsonParser();
    private Base64.Decoder decoder = Base64.getUrlDecoder();

    public String requestAccessToken(OauthRequestParam params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUrl);

        HttpEntity request = new HttpEntity(body, httpHeaders);

        if (request == null)
            throw new HttpClientErrorException("Kakao 토큰을 요청할 수 없습니다.");

        OauthTokens response = restTemplate.postForObject(tokenUrl, request, OauthTokens.class);
        log.info("idToken ={}", response.getIdToken());

        if (response == null)
            throw new HttpClientErrorException("Kakao 토큰을 발급 받을 수 없습니다.");

        return response.getAccessToken();
    }

    public OauthInfoResponse requestOauthInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity request = new HttpEntity(body, httpHeaders);

        if (request == null)
            throw new HttpClientErrorException("사용자 정보를 Kakao Auth Server로부터 요청할 수 없습니다.");

        OauthInfoResponse response = restTemplate.postForObject(userInfoUrl, request, OauthInfoResponse.class);
        return response;
    }

    /**
     * 사용하는 메소드
     */


    /**
     * parsing 후 header, payload, secret 분리하기
     */
    public Map<String, String> requestInfo(String request){
        Map<String, Object> requestArray = jsonParser.parseMap(request);
        log.info("request={}", requestArray.get("idToken"));
        StringTokenizer st = new StringTokenizer(requestArray.get("idToken").toString(), ".");

        Map<String, String> requestInfo = new HashMap<>();

        requestInfo.put("request", requestArray.get("idToken").toString());
        requestInfo.put("header", st.nextToken());
        requestInfo.put("payload", st.nextToken());

        return requestInfo;
    }

    /**
     * payload 디코딩
     */
    public Map<String, Object> decodedPayload(String payload){
        String decodedPayload = new String(decoder.decode(payload));
        Map<String, Object> payloadArray = jsonParser.parseMap(decodedPayload);
        return payloadArray;
    }

    /**
     * header 디코딩
     */
    public Map<String, Object> decodedHeader(String header){
        String decodedHeader = new String(decoder.decode(header));
        log.info("decodedHeader={}", decodedHeader);
        Map<String, Object> headerArray = jsonParser.parseMap(decodedHeader);
        return headerArray;
    }

    /**
     * aud, iss, exp 검증
     */
    public void validateToken(String token, Map<String, Object> payload, Map<String, Object> header){
        try {
            Jwts.parserBuilder()
                    .requireAudience(payload.get("aud").toString())
                    .requireIssuer(payload.get("iss").toString())
                    .build()
                    .parseClaimsJwt(token);

        } catch (ExpiredJwtException e) { //파싱하면서 만료된 토큰인지 확인.
            throw new NotValidValueException("만료된 토큰입니다.");
        } catch (Exception e) {
            log.error(e.toString());
            throw new NotValidValueException("유효하지 않은 토큰 값입니다.");
        }

    }

    /**
     * header 공개키 목록 조회
     */
    public Claims headerKidInfo(Map<String, Object> header, String token) {

        String kid = header.get("kid").toString();

        PublicKeyResponse keyResponse = restTemplate.getForObject(keyUrl, PublicKeyResponse.class);

        PublicKeyResponse.Keys keys = keyResponse.getKeys().stream()
                .filter(o -> o.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new NotValidValueException("유효하지 않은 kid 값입니다."));

        log.info("keys.e={}",keys.getE());
        log.info("keys.n={}",keys.getN());

        return validateKey(token, keys.getN(), keys.getE()).getBody();
    }

    /**
     * signature 값 검증
     * @return payload 값
     */
    private Jws<Claims> validateKey(String token, String modulus, String exponent){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getRSAPublicKey(modulus, exponent))
                    .build()
                    .parseClaimsJws(token);

        }catch (ExpiredJwtException e){
            throw new NotValidValueException("토큰이 만료되었습니다.");
        }catch (Exception e){
            throw new NotValidValueException("유효하지 않은 키 값입니다.");
        }
    }

    /**
     * RSA 키 생성
     */
    private Key getRSAPublicKey(String modulus, String exponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = decoder.decode(modulus);
        byte[] decodeE = decoder.decode(exponent);
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);

    }



}
