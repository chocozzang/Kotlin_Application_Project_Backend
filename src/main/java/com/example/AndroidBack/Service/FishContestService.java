package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishContest;
import com.example.AndroidBack.Model.FishInfo;

import java.util.List;

public interface FishContestService {
    public List<FishContest> getContestList();

    public FishContest getContest(Long fcid);
}
