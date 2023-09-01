package com.example.AndroidBack.Model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Component
public class FirstDayWeatherDTO {

    private String nowtemp;
    private String obscode;

    private String tidetypeOne;
    private String tidetimeOne;
    private String tidelevelOne;

    private String tidetypeTwo;
    private String tidetimeTwo;
    private String tidelevelTwo;

    private String tidetypeThree;
    private String tidetimeThree;
    private String tidelevelThree;

    private String tidetypeFour;
    private String tidetimeFour;
    private String tidelevelFour;

}
