package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FirstDayWeatherDTO;

public interface FirstDayWeatherService {

    public void setFirstDayWeather(FirstDayWeatherDTO firstDayWeatherDTO);

    public FirstDayWeatherDTO getFirstDayWeather(String obscode);

    public void clearFirstDayWeather();
}
