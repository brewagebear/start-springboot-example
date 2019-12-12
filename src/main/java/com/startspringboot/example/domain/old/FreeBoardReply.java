package com.startspringboot.example.domain.old;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString(exclude = "board")
@Entity
// 인덱스 설계
@Table(name = "tbl_free_replies",
        indexes = {
        @Index(unique = false,
                columnList = "board_bno")
        })
@EqualsAndHashCode(of = "rno")
public class FreeBoardReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String reply;
    private String replier;

    @ManyToOne
    private FreeBoard board;

    @CreationTimestamp
    private Timestamp replydate;
    @UpdateTimestamp
    private Timestamp updatedate;
}
