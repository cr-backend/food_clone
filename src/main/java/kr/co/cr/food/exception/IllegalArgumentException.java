package kr.co.cr.food.exception;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends FoodException {
    public IllegalArgumentException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }

}
