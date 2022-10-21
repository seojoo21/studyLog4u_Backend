# 개인 토이 프로젝트 :: studyLog4u BackEnd Project
-------------------------------------------

## 1. studyLog4u 프로젝트 소개
- studyLog4u는 개발 관련 공부 내용을 기록하고, 기록 당시 함께 등록한 희망 복습 날짜에 해당 기록에 대한 슬랙 알람을 받아 해당 기록을 잊지 않고 다시 한 번 복습할 수 있도록 도와줍니다.
- 비전공 독학 출신 신입 개발자로서 느리지만 꾸준히 개발 공부를 하기 위해 실제 개인적인 사용을 목적으로 제작하였습니다.

## 2. studyLog4u 프로젝트 주요 목표
1. 백엔드와 프론트엔드의 분리
- 백엔드는 RESTful API로서의 기능에 집중하고, 프론트엔드는 API 요청으로 가져온 데이터를 화면에 구현하는 기능에 집중하도록 백엔드는 Springboot, 프론트엔드는 React.js로 분리하여 구현하였습니다.

    - 백엔드: Springboot + JPA + RESTful API
        - RESTful API 개념 및 활용에 익숙해지기 위해 Spring REST Docs를 적용하였습니다.
        - ORM을 공부하기 위해 JPA를 적용하였습니다.
        - Spring Security, OAuth2, JWT를 공부하기 위해 Google OAuth2 로그인을 적용하였습니다.
        - 외부 API 활용에 익숙해지기 위해 Slack API를 사용하였고 Quartz Scheduler를 적용하여 복습 알람 기능을 구현하였습니다.
    - 프론트엔드: React.js
        - jQuery 외의 프론트엔드 기술을 공부하기 위해 React.js로 프론트엔드를 구현하였습니다.

2. 프로젝트 서버 반영 및 실제 서비스 운영
- studyLog4u는 실제로 개발 공부 시 항상 사용하고자 만든 프로젝트이기 때문에 기획 단계에서부터 직접 서버에 프로젝트를 올리고 운영하는데 목표를 두었습니다.
    - 서버는 AWS Lightsail를 사용합니다.
    - 데이터베이스는 Oracle Cloud InfraStructure 프리티어에서 제공하는 Oracle Database를 사용합니다.
    - 도메인은 호스팅케이알을 사용합니다.

## 3. studyLog4u BackEnd 프로젝트 주요 개발 환경
- Springboot 2.7.1
- JDK 17
- Gradle 7.4.1
- Database: Oracle19c

----------------------------------
## BackEnd Release Note
#### 2022.10.02 Version 1.0
- 1차 개발 완료 후 AWS Lightsail 서버 반영 완료. 서비스 시작 

#### 2022.10.12 Version 1.1
- 로컬테스트 DB, 운영 DB 분리
- WebConfig 수정 

#### 2022.10.22 서버 패치
- Swap memory 추가 설정 
- Jenkins Github 연동 및 CI/CD 구축
  <img width="994" alt="스크린샷 2022-10-22 오전 12 10 14" src="https://user-images.githubusercontent.com/98722435/197228962-34803c73-e726-463b-a85b-80ff55c9581b.png">