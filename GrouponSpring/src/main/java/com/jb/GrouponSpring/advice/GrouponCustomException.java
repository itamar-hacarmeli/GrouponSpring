package com.jb.GrouponSpring.advice;


import com.jb.GrouponSpring.exceptions.AddException;
import com.jb.GrouponSpring.exceptions.GetException;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.exceptions.UpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * GrouponCustomException uses for exception handling in Restful API.
 */

@RestController
@ControllerAdvice
public class GrouponCustomException {
    //which exception class was fired
    @ExceptionHandler(value = {UpdateException.class})
    //what to return in response
    @ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
    public ErrorDetail handleUpdateException(Exception e) {
        return new ErrorDetail("UPDATE", e.getMessage());
    }


    @ExceptionHandler(value = {AddException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e) {
        return new ErrorDetail("POST", e.getMessage());
    }


    @ExceptionHandler(value = {GetException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleGrouponException(Exception e) {
        return new ErrorDetail("GET", e.getMessage());
    }

}
