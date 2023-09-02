package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Model.SixthDayWeather;
import com.example.AndroidBack.Model.SixthDayWeatherDTO;
import com.example.AndroidBack.Repository.SixthDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SixthDayWeatherServiceImpl implements SixthDayWeatherService{

    SixthDayWeatherRepository sixthDayWeatherRepository;

    @Override
    public void setSixthDayWeather(SixthDayWeatherDTO sixthDayWeatherDTO) {
        SixthDayWeather sixthDayWeather = new SixthDayWeather();

        sixthDayWeather.setMaxtemp(sixthDayWeatherDTO.getMaxtemp());
        sixthDayWeather.setMintemp(sixthDayWeatherDTO.getMintemp());
        sixthDayWeather.setObscode(sixthDayWeatherDTO.getObscode());

        sixthDayWeather.setTidetypeOne(sixthDayWeatherDTO.getTidetypeOne());
        sixthDayWeather.setTidetimeOne(sixthDayWeatherDTO.getTidetimeOne());
        sixthDayWeather.setTidelevelOne(sixthDayWeatherDTO.getTidelevelOne());

        sixthDayWeather.setTidetypeTwo(sixthDayWeatherDTO.getTidetypeTwo());
        sixthDayWeather.setTidetimeTwo(sixthDayWeatherDTO.getTidetimeTwo());
        sixthDayWeather.setTidelevelTwo(sixthDayWeatherDTO.getTidelevelTwo());

        sixthDayWeather.setTidetypeThree(sixthDayWeatherDTO.getTidetypeThree());
        sixthDayWeather.setTidetimeThree(sixthDayWeatherDTO.getTidetimeThree());
        sixthDayWeather.setTidelevelThree(sixthDayWeatherDTO.getTidelevelThree());

        sixthDayWeather.setTidetypeFour(sixthDayWeatherDTO.getTidetypeFour());
        sixthDayWeather.setTidetimeFour(sixthDayWeatherDTO.getTidetimeFour());
        sixthDayWeather.setTidelevelFour(sixthDayWeatherDTO.getTidelevelFour());

        sixthDayWeatherRepository.save(sixthDayWeather);
    }

    @Override
    public SecondDayWeatherDTO getSixthDayWeather() {
        return null;
    }

    @Override
    public void clearSixthDayWeather() {
        sixthDayWeatherRepository.clearTable();
    }
}
