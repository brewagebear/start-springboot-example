package com.startspringboot.example.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "member")
@Entity
@Table(name = "TBL_PROFILE")
@EqualsAndHashCode(of = "id")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FNO")
    private Long id;

    @Column(name = "FNAME")
    private String fileName;

    @Column(name = "CURRENT", columnDefinition = "boolean default false")
    private boolean current;

    @ManyToOne
    private Member member;
}
