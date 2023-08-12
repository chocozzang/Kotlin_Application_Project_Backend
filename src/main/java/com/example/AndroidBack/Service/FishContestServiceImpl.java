package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishContest;
import com.example.AndroidBack.Repository.FishContestRepository;
import com.example.AndroidBack.Repository.FishRopeRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FishContestServiceImpl implements FishContestService{

    FishContestRepository fishContestRepository;
    @Override
    public List<FishContest> getContestList() {
        return fishContestRepository.findAll();
    }

    @Override
    public FishContest getContest(Long fcid) {
        return fishContestRepository.findByFcid(fcid);
    }
}
