//package com.studyLog4u.service;
//
//import com.studyLog4u.dto.ReviewDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class ReviewServiceTests {
//
//    @Autowired
//    private ReviewService service;
//
//    @Test
//    public void testRegister(){
//        Long studyId = 20220723135858990L;
//        ReviewDto reviewDto = ReviewDto.builder().content("리뷰 테스트").studyId(studyId).build();
//        System.out.println("======RESULT===========");
//        System.out.println(service.register(reviewDto));
//    }
//
//    @Test
//    public void testGetList(){
//        Long studyId = 20220723140943854L;
//        List<ReviewDto> list = service.getList(studyId);
//        list.forEach(reviewDto -> System.out.println(reviewDto));
//    }
//
//}
