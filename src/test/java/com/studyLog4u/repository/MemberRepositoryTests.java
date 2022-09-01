//package com.studyLog4u.repository;
//
//import com.studyLog4u.entity.Member;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class MemberRepositoryTests {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void testFindMember(){
//        List<Member> list = memberRepository.findByEmail("kangj2016@gmail.com");
//        System.out.println("=================RESULT=================");
//        list.forEach(member -> System.out.println(member));
//        System.out.println("=========================================");
//    }
//}
