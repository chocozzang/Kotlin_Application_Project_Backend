package com.example.AndroidBack.Model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
public class FirstDayWeather {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fdwid;

    @Column(name = "nowtemp")
    private String nowtemp;

    @Column(name = "obscode")
    private String obscode;

    @Column(name = "tidetypeone")
    private String tidetypeOne;
    @Column(name = "tidetimeone")
    private String tidetimeOne;
    @Column(name = "tidelevelone")
    private String tidelevelOne;

    @Column(name = "tidetypetwo")
    private String tidetypeTwo;
    @Column(name = "tidetimetwo")
    private String tidetimeTwo;
    @Column(name = "tideleveltwo")
    private String tidelevelTwo;

    @Column(name = "tidetypethree")
    private String tidetypeThree;
    @Column(name = "tidetimethree")
    private String tidetimeThree;
    @Column(name = "tidelevelthree")
    private String tidelevelThree;

    @Column(name = "tidetypefour")
    private String tidetypeFour;
    @Column(name = "tidetimefour")
    private String tidetimeFour;
    @Column(name = "tidelevelfour")
    private String tidelevelFour;


}
