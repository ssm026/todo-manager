package com.kakaopay.todomanager.common;

import com.kakaopay.todomanager.common.model.ResponseCode;
import com.kakaopay.todomanager.common.model.TodoException;
import com.kakaopay.todomanager.common.model.TodoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TodoException.class)
    @ResponseBody
    public TodoResult tcallExceptionHandler(HttpServletRequest request, TodoException ex) {
        log.error(ex.getErrorCode().getMessage());
        return TodoResult.makeErrorResult(ex.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public TodoResult handle(HttpMessageNotReadableException e) {
        log.error("", e);
        return TodoResult.makeErrorResult(ResponseCode.BAD_PARAMETER, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public TodoResult handle(HttpServletRequest request, MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldError().getField() + " "
                + e.getBindingResult().getFieldError().getDefaultMessage();
        log.error(errorMessage);
        return TodoResult.makeErrorResult(ResponseCode.BAD_PARAMETER, errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public TodoResult handle(HttpServletRequest request, MissingPathVariableException e) {
        log.error(e.getMessage());
        return TodoResult.makeErrorResult(ResponseCode.BAD_PARAMETER, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public TodoResult handle(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error(e.getMessage());
        return TodoResult.makeErrorResult(ResponseCode.BAD_PARAMETER, e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public TodoResult handle(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return TodoResult.makeErrorResult(ResponseCode.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public TodoResult handle(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage());
        return TodoResult.makeErrorResult(ResponseCode.MEDIA_TYPE_NOT_ALLOWED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public TodoResult handle(HttpServletRequest request, BadCredentialsException e) {
        log.error(e.getMessage());
        return TodoResult.makeErrorResult(ResponseCode.LOGIN_FAIL);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public TodoResult handleException(HttpServletRequest request, Exception e) {
        log.error("", e);
        return TodoResult.makeErrorResult(ResponseCode.INTERNAL_SERVER_ERROR);
    }
}
