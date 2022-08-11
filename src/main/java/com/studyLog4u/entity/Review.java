package com.studyLog4u.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="review_board")
@ToString(exclude = "study")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    private Long id;
    @Column(columnDefinition = "clob")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    /* 수정 메서드 */
    public void changeContent(String content) {
        this.content = content;
    }
}
