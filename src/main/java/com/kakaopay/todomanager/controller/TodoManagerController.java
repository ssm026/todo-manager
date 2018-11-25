package com.kakaopay.todomanager.controller;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.kakaopay.todomanager.model.domain.RegistTaskRequest;
import com.kakaopay.todomanager.model.domain.UpdateTaskNameRequest;
import com.kakaopay.todomanager.model.domain.common.TodoResult;
import com.kakaopay.todomanager.service.TodoManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/task")
public class TodoManagerController {
    @Autowired
    private TodoManagerService todoManagerService;

    @GetMapping
    public TodoResult getTodoTaskList(@PageableDefault(size=10, page=0) Pageable pageable) {
        log.info("page : {}", pageable);
        return TodoResult.makeSuccessResult(todoManagerService.getTaskList(pageable));
    }

    @GetMapping("/id/list")
    public TodoResult getNotFinishedIdList() {
        return TodoResult.makeSuccessResult(todoManagerService.getNotFinishedIdList());
    }

    @PostMapping
    public TodoResult registTodoTask(@RequestBody @Valid RegistTaskRequest request) {
        todoManagerService.registTask(request);
        return TodoResult.makeSuccessResult();
    }

    @PatchMapping("/{taskId}")
    public TodoResult modifyTodoTask(@RequestBody @Valid UpdateTaskNameRequest request,
                                     @PathVariable(name = "taskId") Integer taskId) {
        todoManagerService.modifyTaskName(taskId, request);
        return TodoResult.makeSuccessResult();
    }

    @PostMapping("/{taskId}/finish")
    public TodoResult finishTask() {
        return TodoResult.makeSuccessResult();
    }
}
