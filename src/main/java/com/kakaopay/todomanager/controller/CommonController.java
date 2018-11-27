package com.kakaopay.todomanager.controller;

import com.kakaopay.todomanager.common.model.ResponseCode;
import com.kakaopay.todomanager.common.model.TodoException;
import com.kakaopay.todomanager.model.CustomUserDetails;
import com.kakaopay.todomanager.model.dto.MemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */
public class CommonController {
    protected MemberDTO getMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( null == authentication )
            throw new TodoException(ResponseCode.FORBIDDEN);

        Object principal = authentication.getPrincipal();

        if( null != principal && principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return MemberDTO.builder().memberId(userDetails.getMemberId()).name(userDetails.getUsername()).build();
        }

        throw new TodoException(ResponseCode.FORBIDDEN);
    }
}
