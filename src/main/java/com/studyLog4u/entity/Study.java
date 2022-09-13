package com.studyLog4u.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="study_board")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Study extends BaseEntity {

    @Id
    private Long id;
    @Column(columnDefinition = "varchar2(100)", nullable = false)
    private String category;
    @Column(columnDefinition = "varchar2(200)", nullable = false)
    private String title;
    @Column(columnDefinition = "clob")
    private String content;
    @Column(name="notidate")
    private LocalDateTime notiDate;

    @Column(columnDefinition = "varchar2(100)")
    private String userId;

    @Column(columnDefinition = "varchar2(100)")
    private String nickname;

    /* 수정 메서드 */
    public void changeCategory(String category){
        this.category = category;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeNotiDate(LocalDateTime notiDate){
        this.notiDate = notiDate;
    }

}
