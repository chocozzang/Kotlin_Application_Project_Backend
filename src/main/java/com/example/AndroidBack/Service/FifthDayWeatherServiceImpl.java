package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FifthDayWeather;
import com.example.AndroidBack.Model.FifthDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Repository.FifthDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FifthDayWeatherServiceImpl implements FifthDayWeatherService{

    FifthDayWeatherRepository fifthDayWeatherRepository;

    @Override
    public void setFifthDayWeather(FifthDayWeatherDTO fifthDayWeatherDTO) {
        FifthDayWeather fifthDayWeather = new FifthDayWeather();

        fifthDayWeather.setMaxtemp(fifthDayWeatherDTO.getMaxtemp());
        fifthDayWeather.setMintemp(fifthDayWeatherDTO.getMintemp());
        fifthDayWeather.setObscode(fifthDayWeatherDTO.getObscode());

        fifthDayWeather.setTidetypeOne(fifthDayWeatherDTO.getTidetypeOne());
        fifthDayWeather.setTidetimeOne(fifthDayWeatherDTO.getTidetimeOne());
        fifthDayWeather.setTidelevelOne(fifthDayWeatherDTO.getTidelevelOne());

        fifthDayWeather.setTidetypeTwo(fifthDayWeatherDTO.getTidetypeTwo());
        fifthDayWeather.setTidetimeTwo(fifthDayWeatherDTO.getTidetimeTwo());
        fifthDayWeather.setTidelevelTwo(fifthDayWeatherDTO.getTidelevelTwo());

        fifthDayWeather.setTidetypeThree(fifthDayWeatherDTO.getTidetypeThree());
        fifthDayWeather.setTidetimeThree(fifthDayWeatherDTO.getTidetimeThree());
        fifthDayWeather.setTidelevelThree(fifthDayWeatherDTO.getTidelevelThree());

        fifthDayWeather.setTidetypeFour(fifthDayWeatherDTO.getTidetypeFour());
        fifthDayWeather.setTidetimeFour(fifthDayWeatherDTO.getTidetimeFour());
        fifthDayWeather.setTidelevelFour(fifthDayWeatherDTO.getTidelevelFour());

        fifthDayWeatherRepository.save(fifthDayWeather);
    }

    @Override
    public SecondDayWeatherDTO getFifthDayWeather() {
        return null;
    }

    @Override
    public void clearFifthDayWeather() {
        fifthDayWeatherRepository.clearTable();
    }
}
