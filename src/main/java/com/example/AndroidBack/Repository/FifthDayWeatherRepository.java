package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FifthDayWeather;
import com.example.AndroidBack.Model.FourthDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface FifthDayWeatherRepository extends JpaRepository<FifthDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate fifth_day_weather", nativeQuery = true)
    public void clearTable();

}
