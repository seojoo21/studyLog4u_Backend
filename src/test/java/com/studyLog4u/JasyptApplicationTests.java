//package com.studyLog4u;
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class JasyptApplicationTests {
//
//    @Test
//    void jasypt() {
//        String url = "";
//        String userName = "";
//        String password = "";
//
//        System.out.println("===========RESULT============");
//        System.out.println(jasyptEncoding(url));
//        System.out.println(jasyptEncoding(userName));
//        System.out.println(jasyptEncoding(password));
//        System.out.println("===========RESULT============");
//    }
//
//    public String jasyptEncoding(String value){
//        String key = "my_jasypt_key";
//        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
//        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
//        pbeEnc.setPassword(key);
//        return pbeEnc.encrypt(value);
//    }
//}
