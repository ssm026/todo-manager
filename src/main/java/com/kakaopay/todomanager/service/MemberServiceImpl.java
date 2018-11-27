package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.CustomUserDetails;
import com.kakaopay.todomanager.model.dto.MemberDTO;
import com.kakaopay.todomanager.model.entity.Member;
import com.kakaopay.todomanager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void joinMember(MemberDTO request) {
        Member member = Member.builder().name(request.getName())
                        .password(bCryptPasswordEncoder.encode(request.getPassword())).build();

        memberRepository.save(member);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username);

        if( null == member ) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(member, AuthorityUtils.createAuthorityList("USER"));
    }
}
