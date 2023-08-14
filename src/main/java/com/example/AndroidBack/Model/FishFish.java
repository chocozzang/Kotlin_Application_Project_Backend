package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class FishFish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ffid;

    @Column(name = "fishtitle")
    private String fishtitle;

    @Column(name = "fishvideourl")
    private String fishvideourl;

    @Column(name = "fishthumbnail")
    private String fishthumbnail;

}