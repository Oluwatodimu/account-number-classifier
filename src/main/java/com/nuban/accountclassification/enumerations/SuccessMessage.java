package com.nuban.accountclassification.enumerations;

public enum SuccessMessage {
    SUCCESS_MESSAGE(200, "Successful call");

    SuccessMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
