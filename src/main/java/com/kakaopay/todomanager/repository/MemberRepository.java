package com.kakaopay.todomanager.repository;

import com.kakaopay.todomanager.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByName(String name);

    Member findByMemberId(Integer memberId);
}

