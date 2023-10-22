package kr.co.cr.food.exception.advice;

import kr.co.cr.food.exception.InternalServerErrorException;
import kr.co.cr.food.exception.NotFoundException;
import kr.co.cr.food.exception.OauthErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> notFoundExceptionHandler(NotFoundException e){
        logError(e);
        ErrorResult errorResult = new ErrorResult("NotFoundException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> internalServerErrorExceptionHandler(InternalServerErrorException e){
        logError(e);
        ErrorResult errorResult = new ErrorResult("InternalServerErrorException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> oauthErrorExceptionHandler(OauthErrorException e){
        logError(e);
        ErrorResult errorResult = new ErrorResult("OauthErrorException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.FORBIDDEN);
    }

    public void logError(Exception e){
        log.error("[exceptionHandler] ", e);
    }
}
