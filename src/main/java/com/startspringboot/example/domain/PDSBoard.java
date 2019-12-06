package com.startspringboot.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_PDS")
@EqualsAndHashCode(of="pid")
public class PDSBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String pname;
    private String pwriter;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pdsno")
    private List<PDSFile> files;


}
