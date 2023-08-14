package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Model.FishFish;
import com.example.AndroidBack.Repository.FishBaitRepository;
import com.example.AndroidBack.Repository.FishFishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FishFishServiceImpl implements FishFishService{

    FishFishRepository fishFishRepository;

    @Override
    public List<FishFish> getFishList() {
        return fishFishRepository.findAll();
    }

    @Override
    public FishFish getFish(Long ffid) {
        return fishFishRepository.findByFfid(ffid);
    }
}