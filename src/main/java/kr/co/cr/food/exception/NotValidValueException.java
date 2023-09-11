package kr.co.cr.food.exception;

import org.springframework.http.HttpStatus;

public class NotValidValueException extends FoodException {

    public NotValidValueException(String message) {
        super(message, HttpStatus.FORBIDDEN.value());
    }
}
