package kr.co.cr.food.login;

import kr.co.cr.food.entity.Member;
import kr.co.cr.food.login.config.KakaoApiClient;
import kr.co.cr.food.login.config.OauthInfoResponse;
import kr.co.cr.food.login.config.OauthRequestParam;
import kr.co.cr.food.login.jwt.AuthTokens;
import kr.co.cr.food.login.jwt.AuthTokensGenerator;
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

        return memberRepository.save(member).getId();
    }


}
