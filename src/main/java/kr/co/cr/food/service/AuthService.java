package kr.co.cr.food.service;

import kr.co.cr.food.entity.Member;
import kr.co.cr.food.exception.InternalServerErrorException;
import kr.co.cr.food.repository.MemberRepository;
import kr.co.cr.food.utils.KakaoApiClient;
import kr.co.cr.food.dto.auth.OauthInfoResponse;
import kr.co.cr.food.utils.OauthRequestParam;
import kr.co.cr.food.dto.auth.AuthTokens;
import kr.co.cr.food.utils.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final KakaoApiClient kakaoApiClient;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokens login(OauthRequestParam params){
        String accessToken = kakaoApiClient.requestAccessToken(params);
        OauthInfoResponse oauthInfoResponse = kakaoApiClient.requestOauthInfo(accessToken);
        Long memberId = findOrCreateMember(oauthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OauthInfoResponse response) {
        return memberRepository.findByEmail(response.getEmail())
                .map(Member::getId)
                .orElseGet(()-> newMember(response));

    }

    private Long newMember(OauthInfoResponse response) {
        Member member = Member.builder()
                .email(response.getEmail())
                .nickname(response.getNickname())
                .build();

        Long savedId = memberRepository.save(member).getId();

        if(savedId == null){
            throw new InternalServerErrorException("회원 정보가 저장되지 않았습니다.");
        }

        return memberRepository.save(member).getId();
    }


}
