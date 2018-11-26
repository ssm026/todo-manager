package com.kakaopay.todomanager.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
@NoArgsConstructor
public class TaskIdListResponse {
    private List<Integer> taskIdList;

    public TaskIdListResponse(List<Integer> taskIdList) {
        this.taskIdList = taskIdList;
    }
}
