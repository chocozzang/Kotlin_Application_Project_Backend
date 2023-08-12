package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class FishRope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long frid;

    @Column(name = "ropetitle")
    private String ropetitle;

    @Column(name = "ropevideourl")
    private String ropevideourl;

    @Column(name = "ropethumbnail")
    private String ropethumbnail;

}