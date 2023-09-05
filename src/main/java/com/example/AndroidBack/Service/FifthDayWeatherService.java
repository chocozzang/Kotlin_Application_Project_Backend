package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FifthDayWeatherDTO;
import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;

public interface FifthDayWeatherService {

    public void setFifthDayWeather(FifthDayWeatherDTO fifthDayWeatherDTO);

    public OtherDayWeatherDTO getFifthDayWeather(String obscode);

    public void clearFifthDayWeather();
}
