package com.kakaopay.todomanager.model;

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

        result.setCode(ResponseCodeEnum.SUCCESS.getCode());
        result.setMessage(ResponseCodeEnum.SUCCESS.getMessage());

        log.info("Response : [{}]", result.getCode());

        return result;
    }

    public static TodoResult makeSuccessResult(Object data) {
        TodoResult result = new TodoResult();

        result.setCode(ResponseCodeEnum.SUCCESS.getCode());
        result.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        result.setData(data);

        log.info("Response : [{}]", result.getCode());

        return result;
    }
}
