package kr.co.cr.food.common;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class APIResponse<T> {
    private String result;
    private String msg;
    private T data;

    public static <T> APIResponse<T> ok(T data) {
        return APIResponse.<T>builder()
              .result("OK")
              .msg("요청이 완료되었습니다.")
              .data(data)
              .build();
    }
}
