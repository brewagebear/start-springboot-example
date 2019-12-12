package com.startspringboot.example.domain.old;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "replies")
@Entity
@Table(name="tbl_freeboards")
@EqualsAndHashCode
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String writer;
    private String content;


    /***
     * FetchType.EAGER의 경우 DB 성능이 문제 될 수가 있다.
     * 따라서, 이를 해결하기위해서는 @Query를 이용해서 조인 처리하는 것이다.
     */
    //    @OneToMany(mappedBy = "board",
    //            cascade = CascadeType.ALL,
    //            fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "board",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<FreeBoardReply> replies;

    @CreationTimestamp
    private Timestamp regdate;
    @CreationTimestamp
    private Timestamp updatedate;
}




