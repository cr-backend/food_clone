package kr.co.cr.food.common;

import kr.co.cr.food.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    protected Long retrieveMemberId(String token) {
        if (token == null)
            return null;
        String sub = jwtTokenProvider.extractSub(token);
        if (sub == null)
            return null;

        return Long.valueOf(token);
    }

}
