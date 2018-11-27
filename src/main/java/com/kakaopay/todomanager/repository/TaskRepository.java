package com.kakaopay.todomanager.repository;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByMember(Member member, Pageable pageable);

    List<Task> findByMemberAndTaskIdInAndFinishFlag(Member member, List<Integer> taskIdList, Boolean finishFlag);

    @Query(
            value = "select task_id from task where member_id = ?1 AND finish_flag = ?2",
            nativeQuery = true
    )
    List<Integer> findTaskIdByMemberAndFinishFlag(Integer memberId, Boolean finishFlag);

    Task findByMemberAndTaskId(Member member, Integer taskId);
}
