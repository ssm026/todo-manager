package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.dto.RegistTaskRequest;
import com.kakaopay.todomanager.model.dto.TaskListResponse;
import com.kakaopay.todomanager.model.dto.TaskIdListResponse;
import com.kakaopay.todomanager.model.dto.UpdateTaskNameRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */
public interface TodoManagerService {
    TaskListResponse getTaskList(Pageable pageable);

    TaskIdListResponse getNotFinishedIdList();

    void registTask(RegistTaskRequest request);

    void modifyTaskName(Integer taskId, UpdateTaskNameRequest request);

    void finishTask(Integer taskId);
}
