package com.kakaopay.todomanager.model.common;

import com.kakaopay.todomanager.model.common.ResponseCode;
import lombok.Data;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
public class TodoException extends RuntimeException {
    private ResponseCode errorCode;
    private String customMessage;

    public TodoException(ResponseCode errorCode) {
        this.errorCode = errorCode;
        this.customMessage = null;
    }

    public TodoException(ResponseCode errorCode, String customMessage) {
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }
}
