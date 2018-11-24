package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.entity.Reference;
import com.kakaopay.todomanager.entity.Task;
import com.kakaopay.todomanager.model.RegistTaskRequest;
import com.kakaopay.todomanager.model.TaskListResponse;
import com.kakaopay.todomanager.model.common.ResponseCode;
import com.kakaopay.todomanager.model.common.TaskIdListResponse;
import com.kakaopay.todomanager.model.common.TodoException;
import com.kakaopay.todomanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        boolean isReferenceTaskIdListEmpty = true;
        // find not finished task
        if( null != request.getReferenceTaskIdList() && !request.getReferenceTaskIdList().isEmpty() ) {
            isReferenceTaskIdListEmpty = false;

            List<Task> finishedTaskList = taskRepository.findByTaskIdInAndFinishFlag(request.getReferenceTaskIdList(), true);
            log.info("finishTaskList : {}", finishedTaskList);
            if( !finishedTaskList.isEmpty() ) {
                throw new TodoException(ResponseCode.FINISHED_TASK_EXIST);
            }
        }

        // insert
        Task task = Task.builder().name(request.getName()).build();

        if( !isReferenceTaskIdListEmpty ) {
            for( Integer referenceTaskId : request.getReferenceTaskIdList() ) {
                Reference reference = Reference.builder().task(task).referenceTaskId(referenceTaskId).build();
                task.getReferenceList().add(reference);
                log.info("task : {}", reference.getTask().getName());
            }
        }

        log.info("ref : {}", task.getReferenceList());

        taskRepository.save(task);
    }
}
