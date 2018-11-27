package com.kakaopay.todomanager;

import com.kakaopay.todomanager.model.dto.*;
import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.common.model.TodoException;
import com.kakaopay.todomanager.repository.MemberRepository;
import com.kakaopay.todomanager.repository.ReferenceRepository;
import com.kakaopay.todomanager.repository.TaskRepository;
import com.kakaopay.todomanager.service.MemberService;
import com.kakaopay.todomanager.service.TodoManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodomamagerServiceTest {
    @Autowired
    private TodoManagerService todoManagerService;

    @Autowired
    private MemberService memberService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ReferenceRepository referenceRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberRepository realMemberRepository;

    @Test
    public void getTaskListTest() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        PageImpl pageData = new PageImpl(taskList,PageRequest.of(0,3),3);
        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMember(member, PageRequest.of(0,3))).willReturn(pageData);

        TaskListResponseDTO result = todoManagerService.getTaskList(memberDTO, PageRequest.of(0,3));

        assertThat(result.getTaskList()).hasSize(3);
    }

    @Test
    public void insertTaskTest() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        List<Integer> referenceTaskIdList = new ArrayList<Integer>();
        referenceTaskIdList.add(1);
        referenceTaskIdList.add(2);
        RegistTaskRequestDTO request = new RegistTaskRequestDTO("집안일", referenceTaskIdList);

        // check valid reference task
        List<Task> referenceIdList = new ArrayList<Task>();
        referenceIdList.add(new Task());
        referenceIdList.add(new Task());
        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskIdInAndFinishFlag(member, referenceTaskIdList, false)).willReturn(referenceIdList);

        todoManagerService.registTask(memberDTO, request);
    }

    @Test(expected = TodoException.class)
    public void insertTaskFailTest_invalidReferenceIdList() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        List<Integer> referenceTaskIdList = new ArrayList<Integer>();
        referenceTaskIdList.add(1);
        referenceTaskIdList.add(2);
        RegistTaskRequestDTO request = new RegistTaskRequestDTO("집안일", referenceTaskIdList);

        // check valid reference task
        List<Task> referenceIdList = new ArrayList<Task>();
        referenceIdList.add(new Task());
        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskIdInAndFinishFlag(member, referenceTaskIdList, false)).willReturn(referenceIdList);

        todoManagerService.registTask(memberDTO, request);
    }

    @Test(expected = TodoException.class)
    public void insertTaskFailTest_memberNotExist() {
        Member member = null;
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        List<Integer> referenceTaskIdList = new ArrayList<Integer>();
        referenceTaskIdList.add(1);
        referenceTaskIdList.add(2);
        RegistTaskRequestDTO request = new RegistTaskRequestDTO("집안일", referenceTaskIdList);

        // check valid reference task
        List<Task> referenceIdList = new ArrayList<Task>();
        referenceIdList.add(new Task());
        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskIdInAndFinishFlag(member, referenceTaskIdList, false)).willReturn(referenceIdList);

        todoManagerService.registTask(memberDTO, request);
    }

    @Test
    public void getNotFinishedIdList() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        List<Integer> taskIdList = new ArrayList<Integer>();
        taskIdList.add(1);
        taskIdList.add(2);
        System.out.println(taskIdList);
        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findTaskIdByMemberAndFinishFlag(member.getMemberId(), false)).willReturn(taskIdList);

        TaskIdListResponseDTO result = todoManagerService.getNotFinishedIdList(memberDTO);
        System.out.println(taskIdList.size());

        assertThat(result.getTaskIdList()).hasSize(2);
    }

    @Test
    public void modifyTaskNameTest() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        UpdateTaskNameRequestDTO request = new UpdateTaskNameRequestDTO();
        request.setName("집안일");

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskId(member, taskId)).willReturn(task);
        task.setName(request.getName());
        given(taskRepository.save(task)).willReturn(task);

        todoManagerService.modifyTaskName(memberDTO, taskId, request);
    }

    @Test(expected = TodoException.class)
    public void modifyTaskFailTest_notFound() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        UpdateTaskNameRequestDTO request = new UpdateTaskNameRequestDTO();
        request.setName("청소");

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskId(member, taskId)).willReturn(null);

        todoManagerService.modifyTaskName(memberDTO, taskId, request);
    }

    @Test
    public void finishTaskTest() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskId(member, taskId)).willReturn(task);
        task.setFinishFlag(true);
        given(taskRepository.save(task)).willReturn(task);

        todoManagerService.finishTask(memberDTO, taskId);
    }

    @Test(expected = TodoException.class)
    public void finishTaskFailTest_notExist() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskId(member, taskId)).willReturn(null);

        todoManagerService.finishTask(memberDTO, taskId);
    }

    @Test(expected = TodoException.class)
    public void finishTaskFailTest_referenceTaskNotFinished() {
        Member member = Member.builder().memberId(1).name("test").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().memberId(1).name("test").password("test").build();

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(memberRepository.findByMemberId(1)).willReturn(member);
        given(taskRepository.findByMemberAndTaskId(member, taskId)).willReturn(null);
        given(referenceRepository.countNotFinishedReferenceCount(member.getMemberId(), taskId)).willReturn(1);

        todoManagerService.finishTask(memberDTO, taskId);
    }

    @Test
    public void joinMemberTest() {
        Member member = Member.builder().memberId(1).name("test123").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().name("test123").password("test").build();

        given(memberRepository.save(member)).willReturn(member);

        memberService.joinMember(memberDTO);
    }

    @Test
    public void joinMemberFailTest() {
        Member member = Member.builder().name("test123").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().name("test123").password("test").build();

        realMemberRepository.save(member);

        memberService.joinMember(memberDTO);
    }
}
