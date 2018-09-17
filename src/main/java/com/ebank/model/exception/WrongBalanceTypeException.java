package com.ebank.model.exception;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class WrongBalanceTypeException extends Exception {
    public WrongBalanceTypeException() {
        super();
    }

    public WrongBalanceTypeException(String message) {
        super(message);
    }

    public WrongBalanceTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongBalanceTypeException(Throwable cause) {
        super(cause);
    }
}
