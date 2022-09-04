package com.nuban.accountclassification.enumerations;

import lombok.AllArgsConstructor;

/**
 * Author : Todimu Isewon
 * Date : 04/09/2022
 */

public enum ErrorMessages {

    RUNTIME_EXCEPTION(101, "internal server error");

    private final int code;
    private final String message;

    ErrorMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
