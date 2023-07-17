package kr.co.cr.food.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends FoodException{
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
