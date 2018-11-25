package com.kakaopay.todomanager.repository;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */

import com.kakaopay.todomanager.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findAll(Pageable pageable);

    List<Task> findByTaskIdInAndFinishFlag(List<Integer> taskIdList, Boolean finishFlag);

    @Query(
            value = "select task_id from task where finish_flag = ?1",
            nativeQuery = true
    )
    List<Integer> findTaskIdByFinishFlag(Boolean finishFlag);

    Task findByTaskId(Integer taskId);
}
