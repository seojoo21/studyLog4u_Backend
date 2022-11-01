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

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

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
}
