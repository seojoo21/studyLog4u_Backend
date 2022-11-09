package com.studyLog4u.aws;

import com.studyLog4u.entity.FileMng;
import com.studyLog4u.repository.FileMngRepository;
import com.studyLog4u.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsBucketMngService {

    private final FileMngRepository fileMngRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private static final String IMG_ROOT_DIR = "images";

    public List<String> createRemoveFileList() {
        List<FileMng> dbFileList = fileMngRepository.findAllByPathContains(getTargetDir());
        List<String> awsBucketPathList = amazonS3ResourceStorage.getObjectList(getTargetDir());
        List<String> removeFilesList;

        if (awsBucketPathList.isEmpty()) {
            removeFilesList = null;
        } else {
            if (dbFileList.isEmpty()) {
                removeFilesList = awsBucketPathList;
            } else {
                List<String> dbFilePathList = new ArrayList<>();
                for (FileMng file : dbFileList) {
                    dbFilePathList.add(file.getPath().substring(1));
                }
                removeFilesList = awsBucketPathList.stream()
                        .filter(path -> !dbFilePathList.contains(path))
                        .collect(Collectors.toList());
            }
        }

        return removeFilesList;
    }
    private String getTargetDir(){
        LocalDate targetDate = LocalDate.now().minusDays(1); // 어제 날짜
        String targetDir = CommonUtil.getYYYYMMDDDirPath(targetDate, IMG_ROOT_DIR);
        return targetDir;
    }
}
