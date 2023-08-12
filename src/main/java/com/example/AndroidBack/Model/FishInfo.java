package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class FishInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    @Column(name="name")
    private String fishname;

    @Column(name="info")
    private String fishinfo;

    @Column(name="date")
    private String fishdate;

    @Column(name="size")
    private String fishsize;

    @Column(name="popular")
    private String fishpopular;

    @Column(name="place")
    private String fishplace;

    @Column(name="eat")
    private String fisheat;

    @Column(name="imgurl")
    private String imgurl;
}
