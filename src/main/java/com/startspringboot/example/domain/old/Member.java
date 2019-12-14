package com.startspringboot.example.domain.old;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_MEMBERS",
       uniqueConstraints = @UniqueConstraint(columnNames={"UID", "UEM"}))
@EqualsAndHashCode(of="id")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    private Long id;

    @Column(name = "UEM")
    private String userEmail;

    @Column(name = "UPW")
    private String password;

    @Column(name = "UNAME")
    private String userName;
}
