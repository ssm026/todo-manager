package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.domain.UpdateTaskNameRequest;
import com.kakaopay.todomanager.model.entity.Reference;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.model.domain.RegistTaskRequest;
import com.kakaopay.todomanager.model.domain.TaskListResponse;
import com.kakaopay.todomanager.model.domain.common.ResponseCode;
import com.kakaopay.todomanager.model.domain.TaskIdListResponse;
import com.kakaopay.todomanager.model.domain.common.TodoException;
import com.kakaopay.todomanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

@Slf4j
@Service
public class TodoManagerServiceImpl implements TodoManagerService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskListResponse getTaskList(Pageable pageable) {
         return new TaskListResponse(taskRepository.findAll(pageable));
    }

    @Override
    public TaskIdListResponse getNotFinishedIdList() {
        List<Integer> taskIdList = taskRepository.findTaskIdByFinishFlag(false);
        return new TaskIdListResponse(taskIdList);
    }

    @Override
    public void registTask(RegistTaskRequest request) {
        // insert
        Task task = Task.builder().name(request.getName()).build();

        if( null != request.getReferenceTaskIdList() && !request.getReferenceTaskIdList().isEmpty() ) {
            checkFinishedTaskExist(request.getReferenceTaskIdList());

            for( Integer referenceTaskId : request.getReferenceTaskIdList() ) {
                Reference reference = Reference.builder().task(task).referenceTaskId(referenceTaskId).build();
                task.getReferenceList().add(reference);
                log.info("task : {}", reference.getTask().getName());
            }
        }

        taskRepository.save(task);
    }

    private void checkFinishedTaskExist(List<Integer> referenceTaskIdList) {
        List<Task> finishedTaskList = taskRepository.findByTaskIdInAndFinishFlag(referenceTaskIdList, true);
        log.info("finishTaskList : {}", finishedTaskList);
        if( !finishedTaskList.isEmpty() ) {
            throw new TodoException(ResponseCode.FINISHED_TASK_EXIST);
        }
    }

    @Override
    public void modifyTaskName(Integer taskId, UpdateTaskNameRequest request) {
        Task task = taskRepository.findByTaskId(taskId);

        if( null == task ) {
            throw new TodoException(ResponseCode.NOT_FOUND);
        }

        task.setName(request.getName());
        task.setUpdateTime(new Date());
        taskRepository.save(task);
    }
}
