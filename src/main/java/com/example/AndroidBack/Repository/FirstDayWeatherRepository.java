package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FirstDayWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FirstDayWeatherRepository extends JpaRepository<FirstDayWeather, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate first_day_weather", nativeQuery = true)
    public void clearTable();

    public FirstDayWeather findByObscode(String obscode);

}

