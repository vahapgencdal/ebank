package com.ebank.model.exception;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class InSufficientBalanceException extends Exception {
    public InSufficientBalanceException() {
        super();
    }

    public InSufficientBalanceException(String message) {
        super(message);
    }

    public InSufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InSufficientBalanceException(Throwable cause) {
        super(cause);
    }
}
