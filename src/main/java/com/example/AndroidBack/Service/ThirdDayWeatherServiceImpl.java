package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.OtherDayWeatherDTO;
import com.example.AndroidBack.Model.SecondDayWeatherDTO;
import com.example.AndroidBack.Model.ThirdDayWeather;
import com.example.AndroidBack.Model.ThirdDayWeatherDTO;
import com.example.AndroidBack.Repository.ThirdDayWeatherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ThirdDayWeatherServiceImpl implements ThirdDayWeatherService{

    ThirdDayWeatherRepository thirdDayWeatherRepository;

    @Override
    public void setThirdDayWeather(ThirdDayWeatherDTO thirdDayWeatherDTO) {
        ThirdDayWeather thirdDayWeather = new ThirdDayWeather();

        thirdDayWeather.setMaxtemp(thirdDayWeatherDTO.getMaxtemp());
        thirdDayWeather.setMintemp(thirdDayWeatherDTO.getMintemp());
        thirdDayWeather.setObscode(thirdDayWeatherDTO.getObscode());

        thirdDayWeather.setTidetypeOne(thirdDayWeatherDTO.getTidetypeOne());
        thirdDayWeather.setTidetimeOne(thirdDayWeatherDTO.getTidetimeOne());
        thirdDayWeather.setTidelevelOne(thirdDayWeatherDTO.getTidelevelOne());

        thirdDayWeather.setTidetypeTwo(thirdDayWeatherDTO.getTidetypeTwo());
        thirdDayWeather.setTidetimeTwo(thirdDayWeatherDTO.getTidetimeTwo());
        thirdDayWeather.setTidelevelTwo(thirdDayWeatherDTO.getTidelevelTwo());

        thirdDayWeather.setTidetypeThree(thirdDayWeatherDTO.getTidetypeThree());
        thirdDayWeather.setTidetimeThree(thirdDayWeatherDTO.getTidetimeThree());
        thirdDayWeather.setTidelevelThree(thirdDayWeatherDTO.getTidelevelThree());

        thirdDayWeather.setTidetypeFour(thirdDayWeatherDTO.getTidetypeFour());
        thirdDayWeather.setTidetimeFour(thirdDayWeatherDTO.getTidetimeFour());
        thirdDayWeather.setTidelevelFour(thirdDayWeatherDTO.getTidelevelFour());

        thirdDayWeatherRepository.save(thirdDayWeather);
    }

    @Override
    public OtherDayWeatherDTO getThirdDayWeather(String obscode) {
        ThirdDayWeather thirdDayWeather = thirdDayWeatherRepository.findByObscode(obscode);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(thirdDayWeather, OtherDayWeatherDTO.class);
    }

    @Override
    public void clearThirdDayWeather() {
        thirdDayWeatherRepository.clearTable();
    }
}
