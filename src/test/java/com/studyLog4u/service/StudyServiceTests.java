//package com.studyLog4u.service;
//
//import com.studyLog4u.dto.PageRequestDto;
//import com.studyLog4u.dto.PageResultDto;
//import com.studyLog4u.dto.StudyDto;
//import com.studyLog4u.entity.Study;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalTime;
//import java.util.Date;
//
//@SpringBootTest
//public class StudyServiceTests {
//
//    @Autowired
//    private StudyService service;
//
//    @Test
//    public void test(){
//        Long id = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSS").format(LocalTime.now()));
//        System.out.println("===================RESULT=====================");
//        System.out.println(id);
//        System.out.println("===============================================");
//    }
//
////    @Test
////    public void testRegister() {
////        StudyDto studyDto = StudyDto.builder().category("프로그래머스").title("enjoy coding").content("<br>coding</br>").build();
////        System.out.println(service.register(studyDto));
////    }
////
////    @Test
////    public void testList() {
////        PageRequestDto dto = PageRequestDto.builder().page(1).size(10).build();
////        PageResultDto<StudyDto, Study> resultDto = service.getList(dto);
////
////        System.out.println("PREV: " + resultDto.isPrev());
////        System.out.println("NEXT: " + resultDto.isNext());
////        System.out.println("TOTAL: " + resultDto.getTotalPage());
////        System.out.println("==========================================");
////        for(StudyDto studyDto : resultDto.getDtoList()){
////            System.out.println(studyDto);
////        }
////        System.out.println("==========================================");
////        resultDto.getPageList().forEach( i -> {System.out.println(i);});
////    }
////
////    @Test
////    public void testGet() {
////        Long id = 20220723140943854L;
////        StudyDto dto = service.get(id);
////    }
////
////    @Test
////    public void testDelete(){
////        Long id = 20220723161403674L;
////        System.out.println("=========DELETE RESULT================");
////        service.delete(id);
////        System.out.println("========================================");
////    }
////
////    @Test
////    public void testSearch(){
////        PageRequestDto pageRequestDto = PageRequestDto.builder()
////                .page(1)
////                .size(10)
////                .type("t")
////                .keyword("제목")
////                .build();
////
////        PageResultDto<StudyDto, Study> resultDto = service.getList(pageRequestDto);
////
////        System.out.println("PREV: " + resultDto.isPrev());
////        System.out.println("NEXT: " + resultDto.isNext());
////        System.out.println("TOTAL: " + resultDto.getTotalPage());
////        System.out.println("==========================================");
////        for(StudyDto studyDto : resultDto.getDtoList()){
////            System.out.println(studyDto);
////        }
////        System.out.println("==========================================");
////        resultDto.getPageList().forEach( i -> {System.out.println(i);});
////    }
//
//}
