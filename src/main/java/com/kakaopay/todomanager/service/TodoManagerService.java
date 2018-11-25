package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.domain.RegistTaskRequest;
import com.kakaopay.todomanager.model.domain.TaskListResponse;
import com.kakaopay.todomanager.model.domain.TaskIdListResponse;
import com.kakaopay.todomanager.model.domain.UpdateTaskNameRequest;
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
