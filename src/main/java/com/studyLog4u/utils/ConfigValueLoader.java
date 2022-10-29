package com.studyLog4u.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 스프링에서 빈으로 관리되지 않는 일반 클래스에서 yml 또는 properties 파일의 설정값을 가져올 때 사용
 */
public class ConfigValueLoader {

    /**
     * .properties files value loader
     * reference: https://www.baeldung.com/inject-properties-value-non-spring-class
     * @param resourceFileName
     * @return
     * @throws IOException
     */
    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = ConfigValueLoader.class
                                    .getClassLoader()
                                    .getResourceAsStream(resourceFileName);
        properties.load(inputStream);
        inputStream.close();
        return properties ;
    }

    public static Map loadYml(String resourceFileName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = yaml.getClass()
                                    .getClassLoader()
                                    .getResourceAsStream(resourceFileName);
        Map<String, Object> resultMap = yaml.load(inputStream);
        return resultMap;
    }
}
