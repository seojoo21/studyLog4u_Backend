package com.studyLog4u.service;

import com.studyLog4u.dto.MemberDto;
import com.studyLog4u.entity.Member;

public interface MemberService {

    void join(MemberDto dto);
    MemberDto getMember(String email);

    /* DTO to Entity */
    default Member dtoToEntity(MemberDto dto){
        Member entity = Member.builder()
                    .email(dto.getEmail())
                    .nickname(dto.getNickname())
                    .socialType(dto.getSocialType())
                    .build();
        return entity;
    }

    /* Entity to Dto */
    default MemberDto entityToDto(Member entity){
        MemberDto dto = MemberDto.builder()
                    .email(entity.getEmail())
                    .nickname(entity.getNickname())
                    .socialType(entity.getSocialType())
                    .build();
        return dto;
    }
}
