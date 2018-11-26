package com.kakaopay.todomanager.common;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

public enum ResponseCode {
    SUCCESS("TM200", "success"),
    BAD_PARAMETER("TM400", "잘못된 입력값 입니다."),
    NOT_FOUND("TM404", "요청한 할 일이 존재하지 않습니다."),
    METHOD_NOT_ALLOWED("TM405", "허용하지 않는 method 입니다."),
    MEDIA_TYPE_NOT_ALLOWED("TM415", "허용하지 않는 media type 입니다."),
    INTERNAL_SERVER_ERROR("TM500", "요청이 실패했습니다."),
    INVALID_REFERENCE_ID("TM600", "참조 불가능한 ID 가 포함되어있습니다."),
    REFERENCE_TASK_NOT_FINISHED("TM601", "참조된 할 일을 먼저 완료하세요.")
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
