package com.studyLog4u.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class MultipartFileUtil {
    private static final String IMG_ROOT_DIR = "images";

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
        LocalDate date = LocalDate.now();
        String baseDir = CommonUtil.getYYYYMMDDDirPath(date, IMG_ROOT_DIR, fileId, format);

        File pathDirectory = new File(getLocalHomeDirectory(), baseDir);
        if(!pathDirectory.exists()) {
            pathDirectory.mkdirs();
        }

        return baseDir;
    }

    /**
     * yml 파일에서 bucket-domain 값을 가져옴
     * @return domain 주소
     */
    public static String getDomain(){
        Map<String, Object> ymlMap = ConfigValueLoader.loadYml("application-deploy.yml");
        LinkedHashMap<String, Object> cloudMap = (LinkedHashMap<String, Object>) ymlMap.get("cloud");
        LinkedHashMap<String, Object> awsMap= (LinkedHashMap<String, Object>) cloudMap.get("aws");
        String domain = (String) awsMap.get("bucket-domain");

        return domain;
    }
}
