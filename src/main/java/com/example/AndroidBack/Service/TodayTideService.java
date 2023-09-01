package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.TodayTideDTO;

import java.util.List;

public interface TodayTideService {

    public void setTodayTide(TodayTideDTO todayTideDTO);

    public void clearTodayTideTable();

    public List<TodayTideDTO> getTodayTide(String obscode);
}
