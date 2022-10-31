package com.studyLog4u.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="file_board")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileMng extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSeq;

    @Column(columnDefinition = "varchar2(50)")
    private String boardName; // 파일이 업로드된 게시판의 이름

    @Column
    private Long boardId; // 파일이 업로드된 게시판의 게시글 id

    @Column
    private String fileType; // 업로드된 파일의 타입

    @Column
    private String path; // 파일의 실제 물리적 경로

}
