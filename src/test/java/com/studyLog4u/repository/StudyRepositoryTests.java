//package com.studyLog4u.repository;
//
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.studyLog4u.entity.QStudy;
//import com.studyLog4u.entity.Study;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class StudyRepositoryTests {
//
//    @Autowired
//    StudyRepository studyRepository;
//
//    @Test
//    public void testClass(){
//        System.out.println(studyRepository.getClass().getName());
//    }
//
//    //insert test
//    @Test
//    public void testInsertDummies(){
//        IntStream.rangeClosed(1,300).forEach( i -> {
//            Long id = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
//            Study study = Study.builder().id(id).category("백준").title("테스트" + i).content("test....."+i).build();
//            studyRepository.save(study);
//        });
//    }
//
//    //select test
//    @Test
//    public void testSelect(){
//        Long id = 20220723140943854L;
//        Optional<Study> result = studyRepository.findById(id);
//        System.out.println("========================");
//        if(result.isPresent()){
//            Study study = result.get();
//            System.out.println(study);
//        }
//    }
//
//    //update test
//    @Test
//    public void testUpdate(){
//        Long id = 20220720222040337L;
//        Study study = Study.builder().id(id).category("프로그래머스").title("수정").build();
//        System.out.println("================================");
//        System.out.println(studyRepository.save(study));
//    }
//
//    //delete test
////    @Test
////    public void testDelete(){
////        studyRepository.deleteById(20220720000139L);
////    }
//
//    //pagination test
//    @Test
//    public void testPageDefault(){
//        Pageable pageable = PageRequest.of(0,10);
//        Page<Study> result = studyRepository.findAll(pageable);
//        System.out.println(result);
//        System.out.println("--------------------------------");
//        System.out.println("총 페이지 수:" + result.getTotalPages());
//        System.out.println("전체 개수: " + result.getTotalElements());
//        System.out.println("현재 페이지 번호(0부터 시작): " + result.getNumber());
//        System.out.println("페이지당 데이터 개수: " + result.getSize());
//        System.out.println("다음 페이지 존재 여부: " + result.hasNext());
//        System.out.println("시작 페이지(0) 여부: " + result.isFirst());
//
//        System.out.println("--------------------------------");
//        System.out.println("실제 페이지 데이터 처리");
//        for(Study study : result.getContent()){
//            System.out.println(study);
//        }
//    }
//
//    // sorting test
//    @Test
//    public void testSort(){
//        Sort sort1 = Sort.by("id").descending();
//        Pageable pageable = PageRequest.of(0, 10, sort1);
//        Page<Study> result = studyRepository.findAll(pageable);
//        result.get().forEach( study -> {System.out.println(study);} );
//    }
//
////    // querymethod test1 (respository에 메서드 추가해야함)
////    @Test
////    public void testQueryMethods(){
////        List<Study> list = studyRepository.findByIdBetweenOrderByIdDesc(20220720201835715L, 20220720201835568L);
////        for(Study study : list){
////            System.out.println("------");
////            System.out.println(study);
////        }
////    }
//
////    // querymethod test2 (respository에 메서드 추가해야함)
////    @Test
////    public void testQueryMethodWithPageable(){
////        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
////        Page<Study> result = studyRepository.findByIdBetween(20220720201835715L, 20220720201835568L, pageable);
////        result.get().forEach(study -> {System.out.println(study);});
////    }
//
//    //querydsl 단일 항목 검색: 제목에 '1'이 포함된 데이터 검색
//    @Test
//    public void testQuery1(){
//        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
//        QStudy qStudy = QStudy.study; //동적으로 처리하기 위해 Q도메인 클래스를 얻어온다.
//        BooleanBuilder builder = new BooleanBuilder(); //where문에 들어가는 조건들을 넣어주는 컨테이너
//        String keyword = "1"; //검색할 키워드
//        BooleanExpression expression = qStudy.title.contains(keyword); // 원하는 조건을 필드값과 같이 결합하여 생성
//        builder.and(expression); // 위에서 생성한 조건을 where문에 and나 or같은 키워드와 결합
//
//        Page<Study> result = studyRepository.findAll(builder, pageable);
//        result.stream().forEach( study -> {System.out.println(study);} );
//    }
//
//    //querydsl 다중 항목 검색: 제목과 내용에 특정 키워드가 포함된 데이터 검색
//    @Test
//    public void testQuery2(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
//        QStudy qStudy = QStudy.study;
//        String keyword = "9";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression exTitle = qStudy.title.contains(keyword);
//        BooleanExpression exContent = qStudy.content.contains(keyword);
//        BooleanExpression exAll = exTitle.or(exContent); // 제목과 내용 모두 검색될 수 있도록 결합
//        builder.and(exAll);
//
//        Page<Study> result = studyRepository.findAll(builder, pageable);
//        result.stream().forEach( study -> {System.out.println(study);});
//    }
//}
