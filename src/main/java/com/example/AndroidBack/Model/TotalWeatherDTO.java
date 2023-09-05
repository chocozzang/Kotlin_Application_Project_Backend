package com.example.AndroidBack.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @ToString
public class TotalWeatherDTO {

    List<TodayTideDTO> todayTideDTOList;
    FirstDayWeatherDTO firstDayWeatherDTO;
    List<OtherDayWeatherDTO> otherDayWeatherDTOList;

}
