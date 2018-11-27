package com.kakaopay.todomanager.model;

import com.kakaopay.todomanager.model.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */

@Data
public class CustomUserDetails extends User {
    private Integer memberId;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getName(), member.getPassword(), authorities);
        this.memberId = member.getMemberId();
    }
}
