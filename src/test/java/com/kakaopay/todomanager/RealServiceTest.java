package com.kakaopay.todomanager;

import com.kakaopay.todomanager.model.dto.MemberDTO;
import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.repository.MemberRepository;
import com.kakaopay.todomanager.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jyp on 2018. 11. 28.
 * Email :ssm027@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test(expected = DataIntegrityViolationException.class)
    public void joinMemberFailTest_uniqueKey() {
        Member member = Member.builder().name("test123").password("test").build();
        MemberDTO memberDTO = MemberDTO.builder().name("test123").password("test").build();

        memberRepository.save(member);

        memberService.joinMember(memberDTO);
    }
}
