package kr.co.cr.food.diet;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class ApiResponse {

    private String result;
    private String msg;
    private Object data;
}
