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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/task")
public class TodoManagerController {
    @Autowired
    private TodoManagerService todoManagerService;

    @GetMapping
    public TodoResult getTaskList(HttpServletRequest httpServletRequest,
                                  @PageableDefault(size=10, page=0) Pageable pageable) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), pageable);

        return TodoResult.makeSuccessResult(todoManagerService.getTaskList(pageable));
    }

    @GetMapping("/id/list")
    public TodoResult getNotFinishedIdList(HttpServletRequest httpServletRequest) {
        log.info("{}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());

        return TodoResult.makeSuccessResult(todoManagerService.getNotFinishedIdList());
    }

    @PostMapping
    public TodoResult registTask(HttpServletRequest httpServletRequest, @RequestBody @Valid RegistTaskRequest request) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), request);

        todoManagerService.registTask(request);
        return TodoResult.makeSuccessResult();
    }

    @PatchMapping("/{taskId}")
    public TodoResult modifyTaskName(HttpServletRequest httpServletRequest, @RequestBody @Valid UpdateTaskNameRequest request,
                                     @PathVariable(name = "taskId") Integer taskId) {
        log.info("{}, {}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), request, taskId);

        todoManagerService.modifyTaskName(taskId, request);
        return TodoResult.makeSuccessResult();
    }

    @PostMapping("/{taskId}/finish")
    public TodoResult finishTask(HttpServletRequest httpServletRequest, @PathVariable(name = "taskId") Integer taskId) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), taskId);

        todoManagerService.finishTask(taskId);
        return TodoResult.makeSuccessResult();
    }
}
