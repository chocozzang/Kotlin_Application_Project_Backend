package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.SeventhDayWeather;
import com.example.AndroidBack.Model.SixthDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SeventhDayWeatherRepository extends JpaRepository<SeventhDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate seventh_day_weather", nativeQuery = true)
    public void clearTable();

    public SeventhDayWeather findByObscode(String obscode);
}
