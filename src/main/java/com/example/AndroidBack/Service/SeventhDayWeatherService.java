package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Model.SeventhDayWeatherDTO;

public interface SeventhDayWeatherService {

    public void setSeventhDayWeather(SeventhDayWeatherDTO seventhDayWeatherDTO);

    public SecondDayWeatherDTO getSeventhDayWeather();

    public void clearSeventhDayWeather();
}
