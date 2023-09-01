package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FirstDayWeather;
import com.example.AndroidBack.Model.FirstDayWeatherDTO;
import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeather;
import com.example.AndroidBack.Repository.FirstDayWeatherRepository;
import com.example.AndroidBack.Repository.SecondDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SecondDayWeatherServiceImpl implements SecondDayWeatherService{

    SecondDayWeatherRepository secondDayWeatherRepository;

    @Override
    public void setSecondDayWeather(OtherDayWeatherDTO secondDayWeatherDTO) {
        SecondDayWeather secondDayWeather = new SecondDayWeather();

        secondDayWeather.setMaxtemp(secondDayWeatherDTO.getMaxtemp());
        secondDayWeather.setMintemp(secondDayWeatherDTO.getMintemp());
        secondDayWeather.setObscode(secondDayWeatherDTO.getObscode());

        secondDayWeather.setTidetypeOne(secondDayWeatherDTO.getTidetypeOne());
        secondDayWeather.setTidetimeOne(secondDayWeatherDTO.getTidetimeOne());
        secondDayWeather.setTidelevelOne(secondDayWeatherDTO.getTidelevelOne());

        secondDayWeather.setTidetypeTwo(secondDayWeatherDTO.getTidetypeTwo());
        secondDayWeather.setTidetimeTwo(secondDayWeatherDTO.getTidetimeTwo());
        secondDayWeather.setTidelevelTwo(secondDayWeatherDTO.getTidelevelTwo());

        secondDayWeather.setTidetypeThree(secondDayWeatherDTO.getTidetypeThree());
        secondDayWeather.setTidetimeThree(secondDayWeatherDTO.getTidetimeThree());
        secondDayWeather.setTidelevelThree(secondDayWeatherDTO.getTidelevelThree());

        secondDayWeather.setTidetypeFour(secondDayWeatherDTO.getTidetypeFour());
        secondDayWeather.setTidetimeFour(secondDayWeatherDTO.getTidetimeFour());
        secondDayWeather.setTidelevelFour(secondDayWeatherDTO.getTidelevelFour());

        secondDayWeatherRepository.save(secondDayWeather);
    }

    @Override
    public OtherDayWeatherDTO getSecondDayWeather() {
        return null;
    }

    @Override
    public void clearSecondDayWeather() {
        secondDayWeatherRepository.clearTable();
    }
}
