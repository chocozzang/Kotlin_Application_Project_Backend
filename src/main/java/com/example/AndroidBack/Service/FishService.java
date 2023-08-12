package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishInfo;

import java.util.List;

public interface FishService {
    public List<FishInfo> getFishList();

    public FishInfo getFish(Long fid);
}
