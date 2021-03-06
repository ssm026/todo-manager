package com.kakaopay.todomanager;

import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.model.entity.Reference;
import com.kakaopay.todomanager.repository.MemberRepository;
import com.kakaopay.todomanager.repository.ReferenceRepository;
import com.kakaopay.todomanager.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TodomanagerRepositoryTest extends CommonTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTaskTest() {
        Member member = Member.builder().name(UUID.randomUUID().toString()).password("test").build();

        Member savedMember = memberRepository.save(member);

        Task task = Task.builder().member(savedMember).name("task1").finishFlag(true).createTime(new Date()).build();
        Reference reference = Reference.builder().task(task).referenceTaskId(3).build();
        task.getReferenceList().add(reference);
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
    public void getJoinDataTest() {
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

    @Test
    public void getAllDataTest() {
        List<Task> taskList = taskRepository.findAll();

        assertThat(taskList)
                .isNotEmpty()
                .hasSize(4);
    }

    @Test
    public void joinMemberTest() {
        Member member = Member.builder().name("test").password("test").build();

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void joinMemberFailTest() {
        Member member = Member.builder().name("test").password("test").build();

        Member savedMember1 = memberRepository.save(member);
        Member savedMember2 = memberRepository.save(member);
    }
}
