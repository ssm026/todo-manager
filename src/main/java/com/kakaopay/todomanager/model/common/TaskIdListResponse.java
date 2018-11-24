package com.kakaopay.todomanager.model.common;

import lombok.Data;

import java.util.List;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
public class TaskIdListResponse {
    private List<Integer> taskIdList;

    public TaskIdListResponse() {

    }

    public TaskIdListResponse(List<Integer> taskIdList) {
        this.taskIdList = taskIdList;
    }
}
