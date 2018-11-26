package com.kakaopay.todomanager.controller;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.kakaopay.todomanager.model.dto.RegistTaskRequest;
import com.kakaopay.todomanager.model.dto.UpdateTaskNameRequest;
import com.kakaopay.todomanager.common.TodoResult;
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
    public TodoResult getTaskList(@PageableDefault(size=10, page=0) Pageable pageable) {
        return TodoResult.makeSuccessResult(todoManagerService.getTaskList(pageable));
    }

    @GetMapping("/id/list")
    public TodoResult getNotFinishedIdList() {
        return TodoResult.makeSuccessResult(todoManagerService.getNotFinishedIdList());
    }

    @PostMapping
    public TodoResult registTask(@RequestBody @Valid RegistTaskRequest request) {
        todoManagerService.registTask(request);
        return TodoResult.makeSuccessResult();
    }

    @PatchMapping("/{taskId}")
    public TodoResult modifyTaskName(@RequestBody @Valid UpdateTaskNameRequest request,
                                     @PathVariable(name = "taskId") Integer taskId) {
        todoManagerService.modifyTaskName(taskId, request);
        return TodoResult.makeSuccessResult();
    }

    @PostMapping("/{taskId}/finish")
    public TodoResult finishTask(@PathVariable(name = "taskId") Integer taskId) {
        todoManagerService.finishTask(taskId);
        return TodoResult.makeSuccessResult();
    }
}
