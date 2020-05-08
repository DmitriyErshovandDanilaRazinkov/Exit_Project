package com.controller;

import com.exceptions.NotFoundDataBaseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundDataBaseException.class)
    public String notFoundExceptionHandler(Model model, NotFoundDataBaseException ex) {

        model.addAttribute("exceptionText", ex.getMessage());

        return "exception";
    }

}
