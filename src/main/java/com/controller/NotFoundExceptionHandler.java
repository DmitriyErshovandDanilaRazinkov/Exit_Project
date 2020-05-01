package com.controller;

import com.exceptions.NotFoundDataBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundDataBaseException.class)
    public String notFoundExceptionHandler(Model model, NotFoundDataBaseException ex) {

        model.addAttribute("exceptionText", ex.getMessage());

        return "exception";
    }

}
