package com.kakaopay.todomanager;

import com.kakaopay.todomanager.model.dto.UpdateTaskNameRequest;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.model.dto.RegistTaskRequest;
import com.kakaopay.todomanager.model.dto.TaskListResponse;
import com.kakaopay.todomanager.model.dto.TaskIdListResponse;
import com.kakaopay.todomanager.common.TodoException;
import com.kakaopay.todomanager.repository.ReferenceRepository;
import com.kakaopay.todomanager.repository.TaskRepository;
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

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ReferenceRepository referenceRepository;

    @Test
    public void getTaskListTest() {
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        PageImpl pageData = new PageImpl(taskList,PageRequest.of(0,3),3);
        given(taskRepository.findAll(PageRequest.of(0,3))).willReturn(pageData);

        TaskListResponse result = todoManagerService.getTaskList(PageRequest.of(0,3));

        assertThat(result.getTaskList()).hasSize(3);
    }

    @Test
    public void insertTaskTest() {
        List<Integer> referenceTaskIdList = new ArrayList<Integer>();
        referenceTaskIdList.add(1);
        referenceTaskIdList.add(2);
        RegistTaskRequest request = new RegistTaskRequest("집안일", referenceTaskIdList);

        // check valid reference task
        List<Task> referenceIdList = new ArrayList<Task>();
        referenceIdList.add(new Task());
        referenceIdList.add(new Task());
        given(taskRepository.findByTaskIdInAndFinishFlag(referenceTaskIdList, false)).willReturn(referenceIdList)
        ;
        todoManagerService.registTask(request);
    }

    @Test(expected = TodoException.class)
    public void insertTaskFailTest_invalidReferenceIdList() {
        List<Integer> referenceTaskIdList = new ArrayList<Integer>();
        referenceTaskIdList.add(1);
        referenceTaskIdList.add(2);
        RegistTaskRequest request = new RegistTaskRequest("집안일", referenceTaskIdList);

        // check valid reference task
        List<Task> referenceIdList = new ArrayList<Task>();
        referenceIdList.add(new Task());
        given(taskRepository.findByTaskIdInAndFinishFlag(referenceTaskIdList, false)).willReturn(referenceIdList);

        todoManagerService.registTask(request);
    }

    @Test
    public void getNotFinishedIdList() {
        List<Integer> taskIdList = new ArrayList<Integer>();
        taskIdList.add(1);
        taskIdList.add(2);
        System.out.println(taskIdList);
        given(taskRepository.findTaskIdByFinishFlag(false)).willReturn(taskIdList);

        TaskIdListResponse result = todoManagerService.getNotFinishedIdList();
        System.out.println(taskIdList.size());

        assertThat(result.getTaskIdList()).hasSize(2);
    }

    @Test
    public void modifyTaskNameTest() {
        UpdateTaskNameRequest request = new UpdateTaskNameRequest();
        request.setName("집안일");

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(taskRepository.findByTaskId(taskId)).willReturn(task);
        task.setName(request.getName());
        given(taskRepository.save(task)).willReturn(task);

        todoManagerService.modifyTaskName(taskId, request);
    }

    @Test(expected = TodoException.class)
    public void modifyTaskFailTest_notFound() {
        UpdateTaskNameRequest request = new UpdateTaskNameRequest();
        request.setName("청소");

        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(taskRepository.findByTaskId(taskId)).willReturn(null);

        todoManagerService.modifyTaskName(taskId, request);
    }

    @Test
    public void finishTaskTest() {
        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(taskRepository.findByTaskId(taskId)).willReturn(task);
        task.setFinishFlag(true);
        given(taskRepository.save(task)).willReturn(task);

        todoManagerService.finishTask(taskId);
    }

    @Test(expected = TodoException.class)
    public void finishTaskFailTest_notExist() {
        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(taskRepository.findByTaskId(taskId)).willReturn(null);

        todoManagerService.finishTask(taskId);
    }

    @Test(expected = TodoException.class)
    public void finishTaskFailTest_referenceTaskNotFinished() {
        Integer taskId = 1;
        Task task = Task.builder().name("빨래").createTime(new Date()).finishFlag(false).build();
        task.setTaskId(taskId);

        given(taskRepository.findByTaskId(taskId)).willReturn(null);
        given(referenceRepository.countNotFinishedReferenceCount(taskId)).willReturn(1);

        todoManagerService.finishTask(taskId);
    }
}
