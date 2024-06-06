package com.viettel.multitenantmultidatabasedemo.exception;

public class CustomerNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -196895950946473879L;

    public CustomerNotFoundException(String message) {
        super(message);
    }

}
