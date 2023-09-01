package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.SecondDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SecondDayWeatherRepository extends JpaRepository<SecondDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate second_day_weather", nativeQuery = true)
    public void clearTable();

}
