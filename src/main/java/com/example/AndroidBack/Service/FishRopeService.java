package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishInfo;
import com.example.AndroidBack.Model.FishRope;

import java.util.List;

public interface FishRopeService {
    public List<FishRope> getRopeList();

    public FishRope getRope(Long frid);
}
