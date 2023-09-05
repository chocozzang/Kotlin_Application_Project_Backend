package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Model.ThirdDayWeatherDTO;

public interface ThirdDayWeatherService {

    public void setThirdDayWeather(ThirdDayWeatherDTO thirdDayWeatherDTO);

    public OtherDayWeatherDTO getThirdDayWeather(String obscode);

    public void clearThirdDayWeather();
}
