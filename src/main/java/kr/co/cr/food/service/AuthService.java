package kr.co.cr.food.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import kr.co.cr.food.dto.auth.OauthMemberDto;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.exception.InternalServerErrorException;
import kr.co.cr.food.exception.NotValidValueException;
import kr.co.cr.food.repository.MemberRepository;
import kr.co.cr.food.utils.KakaoApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final KakaoApiClient kakaoApiClient;
//    private final AuthTokensGenerator authTokensGenerator;

//    public AuthTokens login(OauthRequestParam params) {
//        String accessToken = kakaoApiClient.requestAccessToken(params);
//        OauthInfoResponse oauthInfoResponse = kakaoApiClient.requestOauthInfo(accessToken);
//        Long memberId = findOrCreateMember(oauthInfoResponse);
//        return authTokensGenerator.generate(memberId);
//    }
//
//    private Long findOrCreateMember(OauthInfoResponse response) {
//        return memberRepository.findByEmail(response.getEmail())
//                .map(Member::getId)
//                .orElseGet(() -> newMember(response));
//
//    }

//    private Long newMember(OauthInfoResponse response) {
//        Member member = Member.builder()
//                .email(response.getEmail())
//                .nickname(response.getNickname())
//                .build();
//
//        Long savedId = memberRepository.save(member).getId();
//
//        if (savedId == null) {
//            throw new InternalServerErrorException("회원 정보가 저장되지 않았습니다.");
//        }
//
//        return memberRepository.save(member).getId();
//    }

    public String validateToken(String request) {
        /**
         * parsing 후 header, payload, secret 분리하기
         */
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> requestArray = jsonParser.parseMap(request);
        log.info("request={}", requestArray.get("idToken"));
        StringTokenizer st = new StringTokenizer(requestArray.get("idToken").toString(), ".");
        String header = st.nextToken();
        String payload = st.nextToken();
//        String secret = st.nextToken();

        /**
         * payload 디코딩
         */
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String decodedPayload = new String(decoder.decode(payload));
        Map<String, Object> payloadArray = jsonParser.parseMap(decodedPayload);

        /**
         * header 디코딩
         */
        String decodedHeader = new String(decoder.decode(header));
        log.info("decodedHeader={}", decodedHeader);
        Map<String, Object> headerArray = jsonParser.parseMap(decodedHeader);

        String token = header + "." + payload + ".";

        try {
            Jwts.parserBuilder()
                    .requireAudience(payloadArray.get("aud").toString())
                    .requireIssuer(payloadArray.get("iss").toString())
                    .build()
                    .parseClaimsJwt(token);

            kakaoApiClient.headerKidInfo(headerArray);

        } catch (ExpiredJwtException e) { //파싱하면서 만료된 토큰인지 확인.
            throw new NotValidValueException("만료된 토큰입니다.");
        } catch (Exception e) {
            log.error(e.toString());
            throw new NotValidValueException("유효하지 않은 토큰 값입니다.");
        }

        String email = payloadArray.get("email").toString();
        String nickname = payloadArray.get("nickname").toString();
        OauthMemberDto dto = new OauthMemberDto(email, nickname);
        findOrCreateMember(dto);

        return "ok";
    }

    private Long findOrCreateMember(OauthMemberDto dto) {
        return memberRepository.findByEmail(dto.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(dto));

    }

    private Long newMember(OauthMemberDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .build();

        Long savedId = memberRepository.save(member).getId();

        if (savedId == null) {
            throw new InternalServerErrorException("회원 정보가 저장되지 않았습니다.");
        }

        return memberRepository.save(member).getId();
    }
}
