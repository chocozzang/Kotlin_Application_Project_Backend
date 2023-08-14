package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Model.FishFish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishFishRepository extends JpaRepository<FishFish, Long> {

    FishFish findByFfid(Long ffid);
}
