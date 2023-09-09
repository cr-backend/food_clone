package kr.co.cr.food.dto.auth;

import lombok.Data;

@Data
public class OauthMemberDto {

    private String email;
    private String nickname;

    public OauthMemberDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
