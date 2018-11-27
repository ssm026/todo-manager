package com.kakaopay.todomanager.controller;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.kakaopay.todomanager.model.dto.MemberDTO;
import com.kakaopay.todomanager.model.dto.RegistTaskRequestDTO;
import com.kakaopay.todomanager.model.dto.UpdateTaskNameRequestDTO;
import com.kakaopay.todomanager.common.model.TodoResult;
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
public class TodoManagerController extends CommonController {
    @Autowired
    private TodoManagerService todoManagerService;

    @GetMapping
    public TodoResult getTaskList(HttpServletRequest httpServletRequest,
                                  @PageableDefault(size=10, page=0) Pageable pageable) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), pageable);
        MemberDTO memberInfo = getMemberInfo();

        return TodoResult.makeSuccessResult(todoManagerService.getTaskList(memberInfo, pageable));
    }

    @GetMapping("/id/list")
    public TodoResult getNotFinishedIdList(HttpServletRequest httpServletRequest) {
        log.info("{}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        MemberDTO memberInfo = getMemberInfo();

        return TodoResult.makeSuccessResult(todoManagerService.getNotFinishedIdList(memberInfo));
    }

    @PostMapping
    public TodoResult registTask(HttpServletRequest httpServletRequest, @RequestBody @Valid RegistTaskRequestDTO request) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), request);
        MemberDTO memberInfo = getMemberInfo();

        todoManagerService.registTask(memberInfo, request);
        return TodoResult.makeSuccessResult();
    }

    @PatchMapping("/{taskId}")
    public TodoResult modifyTaskName(HttpServletRequest httpServletRequest, @RequestBody @Valid UpdateTaskNameRequestDTO request,
                                     @PathVariable(name = "taskId") Integer taskId) {
        log.info("{}, {}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), request, taskId);
        MemberDTO memberInfo = getMemberInfo();

        todoManagerService.modifyTaskName(memberInfo, taskId, request);
        return TodoResult.makeSuccessResult();
    }

    @PostMapping("/{taskId}/finish")
    public TodoResult finishTask(HttpServletRequest httpServletRequest, @PathVariable(name = "taskId") Integer taskId) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), taskId);
        MemberDTO memberInfo = getMemberInfo();

        todoManagerService.finishTask(memberInfo, taskId);
        return TodoResult.makeSuccessResult();
    }
}
