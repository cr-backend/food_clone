package kr.co.cr.food.service;

import io.jsonwebtoken.Claims;
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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<String, Object> validateToken(String request) {

        // parsing
        Map<String, String> requestInfo = kakaoApiClient.requestInfo(request);
        String payload = requestInfo.get("payload");
        String header = requestInfo.get("header");

        // 페이로더 디코딩
        Map<String, Object> payloadArray = kakaoApiClient.decodedPayload(payload);

        // 헤더 디코딩
        Map<String, Object> headerArray = kakaoApiClient.decodedHeader(header);

        // aud, iss, exp 검증
        String token = header + "." + payload + ".";
        kakaoApiClient.validateToken(token, payloadArray);

        // kid, signature 검증
        Claims claims = kakaoApiClient.headerKidInfo(headerArray, requestInfo.get("request"));

        // 로그인
        String email = claims.get("email", String.class);
        String nickname = claims.get("nickname", String.class);
        OauthMemberDto dto = new OauthMemberDto(email, nickname);

        // db 존재: 정보 찾기
        // db 미존재: 저장하기
        Long memberId = findOrCreateMember(dto);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotValidValueException("회원 정보 없음"));

        // response 값: 아이디, 닉네임
        Map<String, Object> result = new HashMap<>();
        result.put("memberId", member.getId());
        result.put("nickName", member.getNickname());

        return result;
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
