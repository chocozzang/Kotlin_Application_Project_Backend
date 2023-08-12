package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class FishBait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fbid;

    @Column(name = "baittitle")
    private String baittitle;

    @Column(name = "baitvideourl")
    private String baitvideourl;

    @Column(name = "baitthumbnail")
    private String baitthumbnail;

}