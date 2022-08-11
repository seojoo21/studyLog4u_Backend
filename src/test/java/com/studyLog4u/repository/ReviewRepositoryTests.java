//package com.studyLog4u.repository;
//
//import com.studyLog4u.entity.Review;
//import com.studyLog4u.entity.Study;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class ReviewRepositoryTests {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Test
//    public void testInsert(){
//        IntStream.rangeClosed(1,5).forEach(i -> {
//            Long studyId = 20220723135858990L;
//            Long id = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
//
//            Study study = Study.builder().id(studyId).build();
//            Review review = Review.builder()
//                    .id(id)
//                    .content("복습...."+i)
//                    .study(study)
//                    .build();
//
//            reviewRepository.save(review);
//        });
//    }
//
//    @Test
//    public void getReview1(){
//        Optional<Review> result = reviewRepository.findById(20220725212622237L);
//        Review review = result.get();
//        System.out.println("==============================");
//        System.out.println(review);
//        System.out.println(review.getStudy());
//        System.out.println("==============================");
//    }
//
//    @Test
//    public void testListByStudy(){
//        Long studyId = 20220723140943854L;
//        List<Review> reviewList = reviewRepository.getReviewsByStudyOrderByIdDesc(Study.builder().id(studyId).build());
//        reviewList.forEach(review -> System.out.println(review));
//    }
//
//    @Test
//    public void testDelete(){
//        Long studyId = 20220723135858990L;
//        reviewRepository.deleteByStudyId(studyId);
//    }
//}
