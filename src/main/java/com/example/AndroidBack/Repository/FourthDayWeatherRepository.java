package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FourthDayWeather;
import com.example.AndroidBack.Model.ThirdDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface FourthDayWeatherRepository extends JpaRepository<FourthDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate fourth_day_weather", nativeQuery = true)
    public void clearTable();

}
