package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Model.SixthDayWeatherDTO;

public interface SixthDayWeatherService {

    public void setSixthDayWeather(SixthDayWeatherDTO sixthDayWeatherDTO);

    public OtherDayWeatherDTO getSixthDayWeather(String obscode);

    public void clearSixthDayWeather();
}
