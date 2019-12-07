package com.startspringboot.example.domain;


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

    @OneToMany(mappedBy = "board",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<FreeBoardReply> replies;

    @CreationTimestamp
    private Timestamp regdate;
    @CreationTimestamp
    private Timestamp updatedate;
}




