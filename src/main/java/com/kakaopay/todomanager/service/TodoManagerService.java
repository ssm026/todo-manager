package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.entity.Task;
import com.kakaopay.todomanager.model.RegistTaskRequest;
import com.kakaopay.todomanager.model.TaskListResponse;
import com.kakaopay.todomanager.model.common.TaskIdListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */
public interface TodoManagerService {
    TaskListResponse getTaskList(Pageable pageable);

    TaskIdListResponse getNotFinishedIdList();

    void registTask(RegistTaskRequest request);
}
