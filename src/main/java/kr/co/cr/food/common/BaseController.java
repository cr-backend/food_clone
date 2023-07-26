package kr.co.cr.food.common;

public abstract class BaseController {

    private final String SUCCESS_MESSAGE = "요청이 완료되었습니다.";
    protected ApiResponse ok(Object data) {
        return ApiResponse.builder()
            .result("OK")
            .message(SUCCESS_MESSAGE)
            .data(data)
            .build();
    }

    protected ApiResponse fail(String message) {
        return ApiResponse.builder()
            .result("FAIL")
            .message(message)
            .build();
    }
}
