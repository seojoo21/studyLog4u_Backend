package com.studyLog4u.service.Impl;

import com.studyLog4u.dto.MemberDto;
import com.studyLog4u.entity.Member;
import com.studyLog4u.repository.MemberRepository;
import com.studyLog4u.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void join(MemberDto dto) {
        Member entity = dtoToEntity(dto);
        memberRepository.save(entity);
        log.info("MemberServiceImpl: join...");
    }

    @Override
    public MemberDto getMember(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        log.info("MemberServiceImpl: getMember...");
        return result.isPresent() ? entityToDto(result.get()) : null;
    }
}
