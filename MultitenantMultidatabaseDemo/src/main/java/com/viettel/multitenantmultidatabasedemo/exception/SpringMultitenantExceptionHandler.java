package com.viettel.multitenantmultidatabasedemo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SpringMultitenantExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    public ModelAndView getCustomersUnavailable(CustomerNotFoundException ex) {
        return new ModelAndView("customers", "error", ex.getMessage());
    }

}
