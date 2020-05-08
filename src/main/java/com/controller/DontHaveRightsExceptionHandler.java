package com.controller;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DontHaveRightsExceptionHandler {

    @ExceptionHandler(DontHaveRightsException.class)
    public String notFoundExceptionHandler(Model model, DontHaveRightsException ex) {

        model.addAttribute("exceptionText", ex.getMessage());

        return "exception";
    }

}