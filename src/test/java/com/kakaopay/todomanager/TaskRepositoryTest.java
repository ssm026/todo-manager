package com.kakaopay.todomanager;

import com.kakaopay.todomanager.entity.Task;
import com.kakaopay.todomanager.entity.Reference;
import com.kakaopay.todomanager.repository.ReferenceRepository;
import com.kakaopay.todomanager.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ReferenceRepository referenceRepository;

    @Before
    public void setData() {
        Task task1 = Task.builder().name("task1").build();
        Task task2 = Task.builder().name("task2").build();
        Task task3 = Task.builder().name("task3").build();
        Task task4 = Task.builder().name("task4").build();
        Reference reference1 = Reference.builder().task(task2).referenceTaskId(1).build();
        Reference reference2 = Reference.builder().task(task3).referenceTaskId(1).build();
        Reference reference3 = Reference.builder().task(task4).referenceTaskId(1).build();
        Reference reference4 = Reference.builder().task(task4).referenceTaskId(3).build();

        task2.getReferenceList().add(reference1);
        task3.getReferenceList().add(reference2);
        task4.getReferenceList().add(reference3);
        task4.getReferenceList().add(reference4);

        testEntityManager.persist(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
    }

    @Test
    public void insertTaskTest() {
        Task task = Task.builder().name("task1").finishFlag(true).createTime(new Date()).build();
        testEntityManager.persist(task);

        List<Task> taskList = taskRepository.findAll();

        assertThat(taskList)
                .isNotEmpty()
                .hasSize(5)
                .contains(task);
    }

    @Test
    public void insertReferenceTest() {
        List<Task> taskList = taskRepository.findAll();
        Reference reference1 = Reference.builder().task(taskList.get(3)).referenceTaskId(taskList.get(1).getTaskId()).build();
        testEntityManager.persist(reference1);

        List<Reference> referenceList = referenceRepository.findAll();

        assertThat(referenceList)
                .isNotEmpty()
                .hasSize(5);

        Reference reference = referenceList.get(0)
                ;
        assertThat(reference.getTask())
                .isEqualTo(taskList.get(1));

        assertThat(reference.getReferenceTaskId())
                .isEqualTo(taskList.get(0).getTaskId());
    }

    @Test
    public void findTaskToReferenceJoinDataTest() {
        List<Task> joinTaskList = taskRepository.findAll();

        assertThat(joinTaskList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4);

        System.out.println(joinTaskList.get(3).toString());
        assertThat(joinTaskList.get(3).getReferenceList())
                .isNotEmpty()
                .hasSize(2);
    }
}
