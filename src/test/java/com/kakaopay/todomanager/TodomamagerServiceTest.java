package com.kakaopay.todomanager;

import com.kakaopay.todomanager.entity.Task;
import com.kakaopay.todomanager.model.RegistTaskRequest;
import com.kakaopay.todomanager.model.TaskListResponse;
import com.kakaopay.todomanager.model.common.TaskIdListResponse;
import com.kakaopay.todomanager.model.common.TodoException;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodomamagerServiceTest extends CommonTest {
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

    @Test(expected = TodoException.class)
    public void insertTaskFailTest() {
        List<Integer> referenceTaskIdList = new ArrayList<Integer>();
        referenceTaskIdList.add(1);
        referenceTaskIdList.add(2);
        RegistTaskRequest request = new RegistTaskRequest("집안일", referenceTaskIdList);

        // check finished reference task
        List<Task> notEmptyList = new ArrayList<Task>();
        notEmptyList.add(new Task());
        given(taskRepository.findByTaskIdInAndFinishFlag(referenceTaskIdList, true)).willReturn(notEmptyList)
        ;
        todoManagerService.registTask(request);
    }

    @Test
    public void insertTaskFailTest_referenceNotExist() {

    }

    @Test
    public void insertTaskFailTest_maxNameLength() {

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
}
