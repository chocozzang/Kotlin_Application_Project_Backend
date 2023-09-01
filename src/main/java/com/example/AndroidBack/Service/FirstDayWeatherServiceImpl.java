package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FirstDayWeather;
import com.example.AndroidBack.Model.FirstDayWeatherDTO;
import com.example.AndroidBack.Repository.FirstDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FirstDayWeatherServiceImpl implements FirstDayWeatherService{

    FirstDayWeatherRepository firstDayWeatherRepository;

    @Override
    public void setFirstDayWeather(FirstDayWeatherDTO firstDayWeatherDTO) {
        FirstDayWeather firstDayWeather = new FirstDayWeather();

        firstDayWeather.setNowtemp(firstDayWeatherDTO.getNowtemp());
        firstDayWeather.setObscode(firstDayWeatherDTO.getObscode());

        firstDayWeather.setTidetypeOne(firstDayWeatherDTO.getTidetypeOne());
        firstDayWeather.setTidetimeOne(firstDayWeatherDTO.getTidetimeOne());
        firstDayWeather.setTidelevelOne(firstDayWeatherDTO.getTidelevelOne());

        firstDayWeather.setTidetypeTwo(firstDayWeatherDTO.getTidetypeTwo());
        firstDayWeather.setTidetimeTwo(firstDayWeatherDTO.getTidetimeTwo());
        firstDayWeather.setTidelevelTwo(firstDayWeatherDTO.getTidelevelTwo());

        firstDayWeather.setTidetypeThree(firstDayWeatherDTO.getTidetypeThree());
        firstDayWeather.setTidetimeThree(firstDayWeatherDTO.getTidetimeThree());
        firstDayWeather.setTidelevelThree(firstDayWeatherDTO.getTidelevelThree());

        firstDayWeather.setTidetypeFour(firstDayWeatherDTO.getTidetypeFour());
        firstDayWeather.setTidetimeFour(firstDayWeatherDTO.getTidetimeFour());
        firstDayWeather.setTidelevelFour(firstDayWeatherDTO.getTidelevelFour());

        firstDayWeatherRepository.save(firstDayWeather);
    }

    @Override
    public FirstDayWeatherDTO getFirstDayWeather() {
        return null;
    }

    @Override
    public void clearFirstDayWeather() {
        firstDayWeatherRepository.clearTable();
    }
}
