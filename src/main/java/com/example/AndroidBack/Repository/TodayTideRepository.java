package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.TodayTide;
import com.example.AndroidBack.Model.TodayTideDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TodayTideRepository extends JpaRepository<TodayTide, Long> {

    @Transactional
    @Modifying
    @Query(value = "truncate today_tide", nativeQuery = true)
    public void cleartable();

    public List<TodayTide> findByObsCode(String obsCode);


}
