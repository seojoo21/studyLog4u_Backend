package com.studyLog4u.security.service;

import com.studyLog4u.entity.Member;
import com.studyLog4u.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByUserId(userId);
        if(member == null){
            throw new UsernameNotFoundException("해당 회원을 찾을 수 없습니다.");
        }
        return CustomUserDetails.create(member);
    }
}
