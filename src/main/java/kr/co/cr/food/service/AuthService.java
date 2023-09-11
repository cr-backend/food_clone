package kr.co.cr.food.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import kr.co.cr.food.dto.auth.AuthTokens;
import kr.co.cr.food.dto.auth.OauthInfoResponse;
import kr.co.cr.food.dto.auth.OauthMemberDto;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.exception.InternalServerErrorException;
import kr.co.cr.food.exception.NotValidValueException;
import kr.co.cr.food.repository.MemberRepository;
import kr.co.cr.food.utils.AuthTokensGenerator;
import kr.co.cr.food.utils.KakaoApiClient;
import kr.co.cr.food.utils.OauthRequestParam;
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
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokens login(OauthRequestParam params) {
        String accessToken = kakaoApiClient.requestAccessToken(params);
        OauthInfoResponse oauthInfoResponse = kakaoApiClient.requestOauthInfo(accessToken);
        Long memberId = findOrCreateMember(oauthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OauthInfoResponse response) {
        return memberRepository.findByEmail(response.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(response));

    }

    private Long newMember(OauthInfoResponse response) {
        Member member = Member.builder()
                .email(response.getEmail())
                .nickname(response.getNickname())
                .build();

        Long savedId = memberRepository.save(member).getId();

        if (savedId == null) {
            throw new InternalServerErrorException("회원 정보가 저장되지 않았습니다.");
        }

        return memberRepository.save(member).getId();
    }

    public Long validateToken(String request) {

        Map<String, String> requestInfo = kakaoApiClient.requestInfo(request);
        String payload = requestInfo.get("payload");
        String header = requestInfo.get("header");

        // 페이로더 디코딩
        Map<String, Object> payloadArray = kakaoApiClient.decodedPayload(payload);

        // 헤더 디코딩
        Map<String, Object> headerArray = kakaoApiClient.decodedHeader(header);

        // aud, iss, exp 검증
        String token = header + "." + payload + ".";
        kakaoApiClient.validateToken(token, payloadArray, headerArray);

        /**
         * secret 검증 필요!!
         */

        // 로그인
        String email = payloadArray.get("email").toString();
        String nickname = payloadArray.get("nickname").toString();
        OauthMemberDto dto = new OauthMemberDto(email, nickname);

        return findOrCreateMember(dto);
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
