package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Model.FishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishBaitRepository extends JpaRepository<FishBait, Long> {

    FishBait findByFbid(Long fbid);
}
