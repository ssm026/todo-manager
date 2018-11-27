package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.dto.*;
import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.model.entity.Reference;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.common.model.ResponseCode;
import com.kakaopay.todomanager.common.model.TodoException;
import com.kakaopay.todomanager.repository.MemberRepository;
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

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public TaskListResponseDTO getTaskList(MemberDTO memberInfo, Pageable pageable) {
        Member member = findMember(memberInfo);

        return new TaskListResponseDTO(taskRepository.findByMember(member, pageable));
    }

    @Override
    public TaskIdListResponseDTO getNotFinishedIdList(MemberDTO memberInfo) {
        Member member = findMember(memberInfo);

        return new TaskIdListResponseDTO(taskRepository.findTaskIdByMemberAndFinishFlag(member.getMemberId(),false));
    }

    @Override
    public void registTask(MemberDTO memberInfo, RegistTaskRequestDTO request) {
        Member member = findMember(memberInfo);

        Task task = Task.builder().member(member).name(request.getName()).build();

        if( null != request.getReferenceTaskIdList() && !request.getReferenceTaskIdList().isEmpty() ) {
            checkRequestReferenceValid(member, request.getReferenceTaskIdList());

            for( Integer referenceTaskId : request.getReferenceTaskIdList() ) {
                Reference reference = Reference.builder().task(task).referenceTaskId(referenceTaskId).build();
                task.getReferenceList().add(reference);
            }
        }

        taskRepository.save(task);
    }

    private void checkRequestReferenceValid(Member member, List<Integer> referenceTaskIdList) {
        List<Task> finishedTaskList = taskRepository.findByMemberAndTaskIdInAndFinishFlag(member, referenceTaskIdList, false);
        log.info("finishTaskList : {}", finishedTaskList);
        if( referenceTaskIdList.size() != finishedTaskList.size() ) {
            throw new TodoException(ResponseCode.INVALID_REFERENCE_ID);
        }
    }

    @Override
    public void modifyTaskName(MemberDTO memberInfo, Integer taskId, UpdateTaskNameRequestDTO request) {
        Member member = findMember(memberInfo);

        Task task = taskRepository.findByMemberAndTaskId(member, taskId);

        if( null == task ) {
            throw new TodoException(ResponseCode.NOT_FOUND);
        }
        log.info("{}", task);

        task.setName(request.getName());
        task.setUpdateTime(new Date());
        taskRepository.save(task);
    }

    @Override
    public void finishTask(MemberDTO memberInfo, Integer taskId) {
        Member member = findMember(memberInfo);

        Task task = taskRepository.findByMemberAndTaskId(member, taskId);

        if( null == task ) {
            throw new TodoException(ResponseCode.NOT_FOUND);
        }
        log.info("{}", task);

        if( 0 < referenceRepository.countNotFinishedReferenceCount(member.getMemberId(), taskId) ) {
            throw new TodoException(ResponseCode.REFERENCE_TASK_NOT_FINISHED);
        }

        task.setFinishFlag(true);
        task.setUpdateTime(new Date());
        taskRepository.save(task);
    }

    private Member findMember(MemberDTO memberInfo) {
        Member member = memberRepository.findByMemberId(memberInfo.getMemberId());

        if( null == member ) {
            throw new TodoException(ResponseCode.FORBIDDEN);
        }
        return member;
    }
}
