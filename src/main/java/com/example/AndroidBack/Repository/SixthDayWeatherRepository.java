package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FifthDayWeather;
import com.example.AndroidBack.Model.SixthDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SixthDayWeatherRepository extends JpaRepository<SixthDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate sixth_day_weather", nativeQuery = true)
    public void clearTable();

}
