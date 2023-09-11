package kr.co.cr.food.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicKeyResponse {

    private List<Keys> keys;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Keys {
        private String kid;
        private String kty;
        private String alg;
        private String use;
        private String n;
        private String e;

    }


}
