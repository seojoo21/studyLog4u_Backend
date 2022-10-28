package com.studyLog4u.utils;

import org.springframework.util.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MultipartUtil {
    private static final String BASE_DIR = "images";

    /**
     * 로컬 또는 서비스가 구동되는 서버에서의 사용자 홈 디렉터리 경로 반환
     * ex) /Users/유저명 (macOS), /home/유저명 (linux)
     * @return
     */
    public static String getLocalHomeDirectory(){
        return System.getProperty("user.home");
    }

    /**
     * 새로운 파일 고유 ID를 생성
     * @return
     */
    public static String createFileId(){
        return UUID.randomUUID().toString();
    }

    /**
     * Multipart의 ContentType값에서 / 이후 확장자만 잘라냄
     * @param contentType ex) image/png
     * @return ex) png
     */
    public static String getFormat(String contentType){
        if(StringUtils.hasText(contentType)) {
            return contentType.substring(contentType.lastIndexOf('/') + 1);
        }
        return null;
    }

    /**
     * 파일의 전체 경로를 생성
     * @param fileId 생성된 파일의 고유 ID
     * @param format 확장자
     * @return
     */
    public static String createPath(String fileId, String format){

        File pathDirectory = new File(getLocalHomeDirectory(), BASE_DIR);
        if(!pathDirectory.exists()) {
            pathDirectory.mkdirs();
        }

//        String path = createDirName();
//        File pathDirectory = new File(getLocalHomeDirectory(), path);
//
//        if(!pathDirectory.exists()) {
//            pathDirectory.mkdirs();
//        }

        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }

    /**
     * 파일이 저장될 디렉토리의 이름을 생성해주는 클래스
     * 연/월/일 디렉토리를 만들고자 오늘 날짜의 경로를 문자열로 반환해줌
     * @return
     */
//    private static String createDirName(){
//        LocalDate date = LocalDate.now();
//        String stringDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        return stringDate.replace("-", File.separator);
//    }
}
