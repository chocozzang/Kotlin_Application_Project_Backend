package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<FishInfo, Long> {

    FishInfo findByFid(Long fid);
}
