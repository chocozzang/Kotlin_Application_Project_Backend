package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Model.FishInfo;

import java.util.List;

public interface FishBaitService {
    public List<FishBait> getBaitList();

    public FishBait getBait(Long fbid);
}
