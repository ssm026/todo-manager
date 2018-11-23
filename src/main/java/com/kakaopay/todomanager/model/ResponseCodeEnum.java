package com.kakaopay.todomanager.model;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

public enum ResponseCodeEnum {
    SUCCESS("TM200", "success"),
    ;

    private final String code;

    private final String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
