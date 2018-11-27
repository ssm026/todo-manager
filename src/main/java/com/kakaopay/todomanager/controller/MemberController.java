package com.kakaopay.todomanager.controller;

import com.kakaopay.todomanager.common.model.TodoResult;
import com.kakaopay.todomanager.model.dto.MemberDTO;
import com.kakaopay.todomanager.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by jyp on 2018. 11. 27.
 * Email :ssm027@gmail.com
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public TodoResult join(HttpServletRequest httpServletRequest,
                           @RequestBody @Valid MemberDTO request) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), request);
        memberService.joinMember(request);

        return TodoResult.makeSuccessResult();
    }

    @PostMapping("/login")
    public TodoResult login(HttpServletRequest httpServletRequest,
                            @RequestBody @Valid MemberDTO request,
                            HttpSession session) {
        log.info("{}, {}, {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), request);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return TodoResult.makeSuccessResult();
    }
}
