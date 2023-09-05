package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FourthDayWeather;
import com.example.AndroidBack.Model.FourthDayWeatherDTO;
import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Repository.FourthDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FourthDayWeatherServiceImpl implements FourthDayWeatherService{

    FourthDayWeatherRepository fourthDayWeatherRepository;

    @Override
    public void setFourthDayWeather(FourthDayWeatherDTO fourthDayWeatherDTO) {
        FourthDayWeather fourthDayWeather = new FourthDayWeather();

        fourthDayWeather.setMaxtemp(fourthDayWeatherDTO.getMaxtemp());
        fourthDayWeather.setMintemp(fourthDayWeatherDTO.getMintemp());
        fourthDayWeather.setObscode(fourthDayWeatherDTO.getObscode());

        fourthDayWeather.setTidetypeOne(fourthDayWeatherDTO.getTidetypeOne());
        fourthDayWeather.setTidetimeOne(fourthDayWeatherDTO.getTidetimeOne());
        fourthDayWeather.setTidelevelOne(fourthDayWeatherDTO.getTidelevelOne());

        fourthDayWeather.setTidetypeTwo(fourthDayWeatherDTO.getTidetypeTwo());
        fourthDayWeather.setTidetimeTwo(fourthDayWeatherDTO.getTidetimeTwo());
        fourthDayWeather.setTidelevelTwo(fourthDayWeatherDTO.getTidelevelTwo());

        fourthDayWeather.setTidetypeThree(fourthDayWeatherDTO.getTidetypeThree());
        fourthDayWeather.setTidetimeThree(fourthDayWeatherDTO.getTidetimeThree());
        fourthDayWeather.setTidelevelThree(fourthDayWeatherDTO.getTidelevelThree());

        fourthDayWeather.setTidetypeFour(fourthDayWeatherDTO.getTidetypeFour());
        fourthDayWeather.setTidetimeFour(fourthDayWeatherDTO.getTidetimeFour());
        fourthDayWeather.setTidelevelFour(fourthDayWeatherDTO.getTidelevelFour());

        fourthDayWeatherRepository.save(fourthDayWeather);
    }

    @Override
    public OtherDayWeatherDTO getFourthDayWeather(String obscode) {
        FourthDayWeather fourthDayWeather = fourthDayWeatherRepository.findByObscode(obscode);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fourthDayWeather, OtherDayWeatherDTO.class);
    }

    @Override
    public void clearFourthDayWeather() {
        fourthDayWeatherRepository.clearTable();
    }
}
