package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.SecondDayWeatherDTO;

public interface SecondDayWeatherService {

    public void setSecondDayWeather(SecondDayWeatherDTO secondDayWeatherDTO);

    public SecondDayWeatherDTO getSecondDayWeather();

    public void clearSecondDayWeather();
}
