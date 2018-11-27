package com.kakaopay.todomanager.service;

import com.kakaopay.todomanager.model.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */
public interface MemberService extends UserDetailsService {
    void joinMember(MemberDTO request);
}
