package com.studyLog4u.security.oauth2;

import com.studyLog4u.security.oauth2.enums.SocialLoginType;
import org.springframework.core.convert.converter.Converter;

public class SocialLoginTypeConverter implements Converter<String, SocialLoginType>{

    // Controller에서 socialLoginType 을 파라미터로 받는데
    // enum 타입의 대문자 값을 소문자로 mapping 가능하도록 하기 위하여 SocialLoginTypeConverter 클래스 생성
    @Override
    public SocialLoginType convert(String source) {
        return SocialLoginType.valueOf(source.toUpperCase());
    }
}
