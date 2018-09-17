package com.ebank.model.exception;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super();
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientBalanceException(Throwable cause) {
        super(cause);
    }
}
