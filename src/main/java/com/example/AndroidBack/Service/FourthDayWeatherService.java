package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;

public interface FourthDayWeatherService {

    public void setFourthDayWeather(OtherDayWeatherDTO fourthDayWeatherDTO);

    public OtherDayWeatherDTO getFourthDayWeather();

    public void clearFourthDayWeather();
}
