package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.domain.UpdateTaskNameRequest;
import com.kakaopay.todomanager.model.entity.Reference;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.model.domain.RegistTaskRequest;
import com.kakaopay.todomanager.model.domain.TaskListResponse;
import com.kakaopay.todomanager.model.domain.common.ResponseCode;
import com.kakaopay.todomanager.model.domain.TaskIdListResponse;
import com.kakaopay.todomanager.model.domain.common.TodoException;
import com.kakaopay.todomanager.repository.ReferenceRepository;
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

    @Autowired
    private ReferenceRepository referenceRepository;

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
        List<Task> finishedTaskList = taskRepository.findByTaskIdInAndFinishFlag(referenceTaskIdList, false);
        log.info("finishTaskList : {}", finishedTaskList);
        if( referenceTaskIdList.size() != finishedTaskList.size() ) {
            throw new TodoException(ResponseCode.INVALID_REFERENCE_ID);
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

    @Override
    public void finishTask(Integer taskId) {
        Task task = taskRepository.findByTaskId(taskId);

        if( null == task ) {
            throw new TodoException(ResponseCode.NOT_FOUND);
        }

        // check 참조
        if( 0 < referenceRepository.countNotFinishedReferenceCount(taskId) ) {
            throw new TodoException(ResponseCode.REFERENCE_TASK_NOT_FINISHED);
        }

        task.setFinishFlag(true);
        task.setUpdateTime(new Date());
        taskRepository.save(task);
    }
}
