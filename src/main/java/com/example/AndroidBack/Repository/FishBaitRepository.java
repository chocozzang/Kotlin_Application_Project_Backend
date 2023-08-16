package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Model.FishInfo;

@Repository
public interface FishBaitRepository extends JpaRepository<FishBait, Long> {

    FishBait findByFbid(Long fbid);
}
