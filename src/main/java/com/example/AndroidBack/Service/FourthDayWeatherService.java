package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FourthDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;

public interface FourthDayWeatherService {

    public void setFourthDayWeather(FourthDayWeatherDTO fourthDayWeatherDTO);

    public SecondDayWeatherDTO getFourthDayWeather();

    public void clearFourthDayWeather();
}
