package com.controller;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DontHaveRightsException.class)
    public String dontHaveRightsException(Model model, DontHaveRightsException ex) {

        model.addAttribute("exceptionText", ex.getMessage());

        return "exception";
    }

    @ExceptionHandler(NotFoundDataBaseException.class)
    public String notFoundExceptionHandler(Model model, NotFoundDataBaseException ex) {

        model.addAttribute("exceptionText", ex.getMessage());

        return "exception";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(java.lang.NullPointerException.class)
    public String Exception(Model model, NullPointerException ex) {

        model.addAttribute("exceptionText", ex.getMessage());

        return "exception";
    }
}