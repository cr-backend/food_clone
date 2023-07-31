package kr.co.cr.food.exception;

import org.springframework.http.HttpStatus;

public class HttpClientErrorException extends FoodException{
    public HttpClientErrorException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
