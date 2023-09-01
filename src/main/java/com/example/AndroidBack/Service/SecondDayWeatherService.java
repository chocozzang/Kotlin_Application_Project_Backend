package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;

public interface SecondDayWeatherService {

    public void setSecondDayWeather(OtherDayWeatherDTO secondDayWeatherDTO);

    public OtherDayWeatherDTO getSecondDayWeather();

    public void clearSecondDayWeather();
}
