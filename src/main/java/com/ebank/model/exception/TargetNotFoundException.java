package com.ebank.model.exception;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class TargetNotFoundException extends Exception {
    public TargetNotFoundException() {
        super();
    }

    public TargetNotFoundException(String message) {
        super(message);
    }

    public TargetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TargetNotFoundException(Throwable cause) {
        super(cause);
    }
}
