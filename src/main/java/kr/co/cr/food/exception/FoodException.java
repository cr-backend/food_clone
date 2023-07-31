package kr.co.cr.food.exception;

import lombok.Getter;

@Getter
public abstract class FoodException extends RuntimeException{

    private final Integer statusCode;

    public FoodException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
