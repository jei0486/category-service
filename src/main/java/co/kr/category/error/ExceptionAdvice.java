package co.kr.category.error;

import co.kr.category.error.exception.CategoryNotFoundException;
import co.kr.category.http.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response validationNotValidException(MethodArgumentNotValidException e) {
        return new Response(co.kr.category.http.HttpStatus.BAD_REQUEST,e.getBindingResult().toString(),null);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Response> categoryNotFoundException(CategoryNotFoundException e) {
        return ResponseEntity.badRequest().body(new Response(co.kr.category.http.HttpStatus.BAD_REQUEST,e.getMessage(),null));
    }

}