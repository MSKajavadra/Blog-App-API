package com.project.blogapp.exceptions;

import com.project.blogapp.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Map<String,String> response=new HashMap<>();
        for(ObjectError error:exception.getBindingResult().getAllErrors()){
            String fieldName=((FieldError)error).getField();
            String defaultMessage=error.getDefaultMessage();
            response.put(fieldName,defaultMessage);
        }
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
