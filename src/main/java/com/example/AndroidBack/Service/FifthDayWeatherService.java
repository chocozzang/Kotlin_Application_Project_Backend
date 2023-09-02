package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FifthDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;

public interface FifthDayWeatherService {

    public void setFifthDayWeather(FifthDayWeatherDTO fifthDayWeatherDTO);

    public SecondDayWeatherDTO getFifthDayWeather();

    public void clearFifthDayWeather();
}
