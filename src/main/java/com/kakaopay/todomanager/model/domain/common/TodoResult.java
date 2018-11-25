package com.kakaopay.todomanager.model.domain.common;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class TodoResult {
    private String code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public static TodoResult makeSuccessResult() {
        TodoResult result = new TodoResult();

        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMessage(ResponseCode.SUCCESS.getMessage());

        log.info("Response : [{}]", result.getCode());

        return result;
    }

    public static TodoResult makeSuccessResult(Object data) {
        TodoResult result = new TodoResult();

        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMessage(ResponseCode.SUCCESS.getMessage());
        result.setData(data);

        log.info("Response : [{}]", result.getCode());

        return result;
    }

    public static TodoResult makeErrorResult(ResponseCode errorCode) {
        TodoResult result = new TodoResult();

        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());

        log.info("Response : [{}, {}]", result.getCode(), result.getMessage());

        return result;
    }

    public static TodoResult makeErrorResult(ResponseCode errorCode, String customMessage) {
        TodoResult result = new TodoResult();

        result.setCode(errorCode.getCode());
        result.setMessage(customMessage);

        log.info("Response : [{}, {}]", result.getCode(), result.getMessage());

        return result;
    }
}
