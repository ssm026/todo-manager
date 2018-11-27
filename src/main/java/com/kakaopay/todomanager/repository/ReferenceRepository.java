package com.kakaopay.todomanager.repository;

import com.kakaopay.todomanager.model.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by jyp on 2018. 11. 23.
 * Email :ssm027@gmail.com
 */
public interface ReferenceRepository extends JpaRepository<Reference, Integer> {
    @Query(
            value = "select count(1) " +
                    "from reference r join task t on (t.member_id = ?1 and r.task_id = t.task_id and t.finish_flag = false) " +
                    "where r.reference_task_id = ?2",
            nativeQuery = true
    )
    Integer countNotFinishedReferenceCount(Integer memberId, Integer taskId);
}
