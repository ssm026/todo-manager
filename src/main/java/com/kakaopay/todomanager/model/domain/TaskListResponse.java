package com.kakaopay.todomanager.model.domain;

import com.kakaopay.todomanager.model.entity.Task;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Data
@NoArgsConstructor
public class TaskListResponse {
    List<Task> taskList;
    private long totalElements;

    public TaskListResponse(Page<Task> taskPageInfo) {
        this.taskList = taskPageInfo.getContent();
        this.totalElements = taskPageInfo.getTotalElements();
    }
}
