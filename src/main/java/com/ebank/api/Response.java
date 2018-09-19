package com.ebank.api;

import lombok.Data;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 19.09.2018
 * @description TODO: Class Description
 */
@Data
public class Response<T> {
    private T data;
    private String message;
    private String code;

    public Response(T data, String message, String code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> Response<T> of(T t, String message, String code) {
        return new Response<>(t, message, code);
    }
}
