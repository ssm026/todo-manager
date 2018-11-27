package com.kakaopay.todomanager.common.model;

import com.kakaopay.todomanager.common.model.ResponseCode;
import lombok.Data;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
public class TodoException extends RuntimeException {
    private ResponseCode errorCode;

    public TodoException(ResponseCode errorCode) {
        this.errorCode = errorCode;
    }
}
