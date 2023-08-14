package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishFish;

import java.util.List;

public interface FishFishService {

    public List<FishFish> getFishList();

    public FishFish getFish(Long ffid);
}
