package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class FishContest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fcid;

    @Column(name = "contestname")
    private String contestname;

    @Column(name = "contestdate")
    private String contestdate;

    @Column(name = "contestposter")
    private String posterimgurl;

}
