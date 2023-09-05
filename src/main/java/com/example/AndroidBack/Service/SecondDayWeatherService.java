package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;

public interface SecondDayWeatherService {

    public void setSecondDayWeather(SecondDayWeatherDTO secondDayWeatherDTO);

    public OtherDayWeatherDTO getSecondDayWeather(String obscode);

    public void clearSecondDayWeather();
}
