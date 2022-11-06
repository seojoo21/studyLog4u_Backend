package com.studyLog4u.utils;

import com.studyLog4u.dto.FileMngDto;
import com.studyLog4u.entity.FileMng;
import org.apache.commons.lang3.ObjectUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 공통 사용 메소드 모음
 * 인스턴스를 생성하지 않고 바로 사용하기 위해 모든 메소드는 static으로 선언한다.
 */
public class CommonUtil {

    /**
     * 실제 게시물에 등록되어 사용되는 이미지 파일의 정보를 List로 만들어 반환한다.
     * @param content 게시물 내용 (이미지를 사용하는 게시물)
     * @param boardName 게시판 이름 (해당 게시물이 속한 게시판의 이름)
     * @param boardId (게시물 번호)
     * @return 이미지 파일 정보가 담긴 리스트
     */
    public static List<FileMng> getUploadedFileList(String content, String boardName, Long boardId){
        List<FileMng> fileList = new ArrayList<>();

        // 1. MarkDown -> HTML Parsing
        Parser parser = Parser.builder().build();
        Node markDownDocument = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String renderedContents = renderer.render(markDownDocument);

        // 2. HTML -> JSoup Parsing
        Element htmlDocument = Jsoup.parse(renderedContents);
        Elements imgTags = htmlDocument.getElementsByTag("img");

        if(!ObjectUtils.isEmpty(imgTags)){
            for(Element imgTag : imgTags){
                String imgUrl = imgTag.attr("src");
                String path = imgUrl.substring(imgUrl.indexOf("/",8)); // https:// 이후부터 substring 할 수 있도록 fromIndex 값 부여

                FileMng fileMng = FileMng.builder()
                                .boardName(boardName)
                                .boardId(boardId)
                                .fileType("image")
                                .path(path)
                                .build();

                fileList.add(fileMng);
            }
        }
        return fileList;
    }

    /**
     * yyyy-MM-dd 형식으로 파일 경로를 만들어 String으로 반환한다.
     * @param date 날짜
     * @param baseDirectory 파일 경로의 첫번째가 되는 base directory
     * @param fileId 파일명 (UUID 값)
     * @param format 파일 확장명 (png, jpg, gif...)
     * @return 파일 경로 (images/2022/11/06/e301sdf1sdf.png)
     */
    public static String getYYYYMMDDDirPath(LocalDate date, String baseDirectory, String fileId, String format){
        int monthValue = date.getMonthValue();
        int dayValue = date.getDayOfMonth();

        String yyyy = String.valueOf(date.getYear());
        String MM = monthValue < 10 ? "0"+String.valueOf(monthValue) : String.valueOf(monthValue);
        String dd = dayValue  < 10 ? "0"+String.valueOf(dayValue) : String.valueOf(dayValue);
        String yyyyMMddDir = baseDirectory + File.separator +
                            yyyy + File.separator +
                            MM + File.separator +
                            dd + File.separator +
                            fileId + "." + format;

        return yyyyMMddDir;
    }

    /**
     * yyyy-MM-dd 형식으로 파일 경로를 만들어 String으로 반환한다.
     * @param date 날짜
     * @param baseDirectory 파일 경로의 첫번째가 되는 base directory
     * @return 파일 경로 (images/2022/11/06)
     */
    public static String getYYYYMMDDDirPath(LocalDate date, String baseDirectory){
        int monthValue = date.getMonthValue();
        int dayValue = date.getDayOfMonth();

        String yyyy = String.valueOf(date.getYear());
        String MM = monthValue < 10 ? "0"+String.valueOf(monthValue) : String.valueOf(monthValue);
        String dd = dayValue  < 10 ? "0"+String.valueOf(dayValue) : String.valueOf(dayValue);
        String yyyyMMddDir = baseDirectory + File.separator +
                yyyy + File.separator +
                MM + File.separator +
                dd;

        return yyyyMMddDir;
    }

}
