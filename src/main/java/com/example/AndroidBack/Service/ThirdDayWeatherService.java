package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;

public interface ThirdDayWeatherService {

    public void setThirdDayWeather(OtherDayWeatherDTO thirdDayWeatherDTO);

    public OtherDayWeatherDTO getThirdDayWeather();

    public void clearThirdDayWeather();
}
