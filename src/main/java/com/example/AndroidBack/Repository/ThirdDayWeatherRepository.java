package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.SecondDayWeather;
import com.example.AndroidBack.Model.ThirdDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ThirdDayWeatherRepository extends JpaRepository<ThirdDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate third_day_weather", nativeQuery = true)
    public void clearTable();

    public ThirdDayWeather findByObscode(String obscode);

}
