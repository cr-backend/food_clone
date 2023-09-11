package kr.co.cr.food.exception;

import kr.co.cr.food.exception.errorcodes.ErrorCode;
import lombok.Getter;

@Getter
public class FoodException extends RuntimeException{

    private final Integer statusCode;

    public FoodException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public FoodException(ErrorCode errorCode) {
        super(errorCode.value);
        this.statusCode = errorCode.code;
    }
}
