package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.dto.*;
import org.springframework.data.domain.Pageable;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */
public interface TodoManagerService {
    TaskListResponseDTO getTaskList(MemberDTO memberInfo, Pageable pageable);

    TaskIdListResponseDTO getNotFinishedIdList(MemberDTO memberInfo);

    void registTask(MemberDTO memberInfo, RegistTaskRequestDTO request);

    void modifyTaskName(MemberDTO memberInfo, Integer taskId, UpdateTaskNameRequestDTO request);

    void finishTask(MemberDTO memberInfo, Integer taskId);
}
