package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.TodayTide;
import com.example.AndroidBack.Model.TodayTideDTO;
import com.example.AndroidBack.Repository.TodayTideRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodayTideServiceImpl implements TodayTideService{

    TodayTideRepository todayTideRepository;

    @Override
    public void setTodayTide(TodayTideDTO todayTideDTO) {
        TodayTide todayTide = new TodayTide();
        todayTide.setObsCode(todayTideDTO.getCode());
        todayTide.setTideLevel(todayTideDTO.getLevel());
        todayTideRepository.save(todayTide);
    }

    @Override
    public void clearTodayTideTable() {
        todayTideRepository.cleartable();
    }

    @Override
    public List<TodayTideDTO> getTodayTide(String obsCode) {
        ModelMapper modelMapper = new ModelMapper();
        List<TodayTide> todayTideList = todayTideRepository.findByObsCode(obsCode);
        return todayTideList.stream()
                .map(todayTide -> modelMapper.map(todayTide, TodayTideDTO.class))
                .collect(Collectors.toList());
    }
}
