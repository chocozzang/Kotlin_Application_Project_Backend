package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class TodayTide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ttid;

    @Column(name = "obscode")
    private String obsCode;

    @Column(name = "tidelevel")
    private String tideLevel;

}
