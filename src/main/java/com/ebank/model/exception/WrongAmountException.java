package com.ebank.model.exception;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class WrongAmountException extends Exception {
    public WrongAmountException() {
        super();
    }

    public WrongAmountException(String message) {
        super(message);
    }

    public WrongAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAmountException(Throwable cause) {
        super(cause);
    }
}
