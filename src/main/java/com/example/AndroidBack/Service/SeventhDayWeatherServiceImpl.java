package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Model.SeventhDayWeather;
import com.example.AndroidBack.Model.SeventhDayWeatherDTO;
import com.example.AndroidBack.Repository.SeventhDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SeventhDayWeatherServiceImpl implements SeventhDayWeatherService{

    SeventhDayWeatherRepository seventhDayWeatherRepository;

    @Override
    public void setSeventhDayWeather(SeventhDayWeatherDTO seventhDayWeatherDTO) {
        SeventhDayWeather seventhDayWeather = new SeventhDayWeather();

        seventhDayWeather.setMaxtemp(seventhDayWeatherDTO.getMaxtemp());
        seventhDayWeather.setMintemp(seventhDayWeatherDTO.getMintemp());
        seventhDayWeather.setObscode(seventhDayWeatherDTO.getObscode());

        seventhDayWeather.setTidetypeOne(seventhDayWeatherDTO.getTidetypeOne());
        seventhDayWeather.setTidetimeOne(seventhDayWeatherDTO.getTidetimeOne());
        seventhDayWeather.setTidelevelOne(seventhDayWeatherDTO.getTidelevelOne());

        seventhDayWeather.setTidetypeTwo(seventhDayWeatherDTO.getTidetypeTwo());
        seventhDayWeather.setTidetimeTwo(seventhDayWeatherDTO.getTidetimeTwo());
        seventhDayWeather.setTidelevelTwo(seventhDayWeatherDTO.getTidelevelTwo());

        seventhDayWeather.setTidetypeThree(seventhDayWeatherDTO.getTidetypeThree());
        seventhDayWeather.setTidetimeThree(seventhDayWeatherDTO.getTidetimeThree());
        seventhDayWeather.setTidelevelThree(seventhDayWeatherDTO.getTidelevelThree());

        seventhDayWeather.setTidetypeFour(seventhDayWeatherDTO.getTidetypeFour());
        seventhDayWeather.setTidetimeFour(seventhDayWeatherDTO.getTidetimeFour());
        seventhDayWeather.setTidelevelFour(seventhDayWeatherDTO.getTidelevelFour());

        seventhDayWeatherRepository.save(seventhDayWeather);
    }

    @Override
    public OtherDayWeatherDTO getSeventhDayWeather(String obscode) {
        SeventhDayWeather seventhDayWeather = seventhDayWeatherRepository.findByObscode(obscode);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(seventhDayWeather, OtherDayWeatherDTO.class);
    }

    @Override
    public void clearSeventhDayWeather() {
        seventhDayWeatherRepository.clearTable();
    }
}
