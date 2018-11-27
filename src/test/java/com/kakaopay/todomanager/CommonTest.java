package com.kakaopay.todomanager;

import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.model.entity.Reference;
import com.kakaopay.todomanager.model.entity.Task;
import com.kakaopay.todomanager.repository.MemberRepository;
import com.kakaopay.todomanager.repository.TaskRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */
public class CommonTest {
    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected MemberRepository memberRepository;


    @Before
    public void setData() {
        Member member = Member.builder().name(UUID.randomUUID().toString()).password("test").build();

        Task task1 = Task.builder().member(member).name("task1").createTime(new Date()).finishFlag(false).build();
        Task task2 = Task.builder().member(member).name("task2").createTime(new Date()).finishFlag(false).build();
        Task task3 = Task.builder().member(member).name("task3").createTime(new Date()).finishFlag(false).build();
        Task task4 = Task.builder().member(member).name("task4").createTime(new Date()).finishFlag(false).build();

        member.getTaskList().add(task1);
        member.getTaskList().add(task2);
        member.getTaskList().add(task3);
        member.getTaskList().add(task4);

        Reference reference1 = Reference.builder().task(task2).referenceTaskId(1).build();
        Reference reference2 = Reference.builder().task(task3).referenceTaskId(1).build();
        Reference reference3 = Reference.builder().task(task4).referenceTaskId(1).build();
        Reference reference4 = Reference.builder().task(task4).referenceTaskId(3).build();

        task2.getReferenceList().add(reference1);
        task3.getReferenceList().add(reference2);
        task4.getReferenceList().add(reference3);
        task4.getReferenceList().add(reference4);

        memberRepository.save(member);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
    }
}
