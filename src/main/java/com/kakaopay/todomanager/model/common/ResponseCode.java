package com.kakaopay.todomanager.model.common;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

public enum ResponseCode {
    SUCCESS("TM200", "success"),
    BAD_PARAMETER("TM400", "잘못된 입력값 입니다."),
    METHOD_NOT_ALLOWED("TM405", "허용하지 않는 method 입니다."),
    MEDIA_TYPE_NOT_ALLOWED("TM415", "허용하지 않는 media type 입니다."),
    INTERNAL_SERVER_ERROR("TM500", "internal server error."),
    FINISHED_TASK_EXIST("TM600", "완료된 할 일을 참조했습니다."),
    ;

    private final String code;

    private final String message;

    ResponseCode(String code, String message) {
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