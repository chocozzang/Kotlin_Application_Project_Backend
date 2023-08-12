package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishInfo;
import com.example.AndroidBack.Repository.FishRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class FishServiceImpl implements FishService{

    FishRepository fishRepository;

    @Override
    public List<FishInfo> getFishList() {
        List<FishInfo> fishes = fishRepository.findAll();
        //System.out.println(fishes.toString());
        return fishes;
    }

    @Override
    public FishInfo getFish(Long fid) {
        FishInfo fish = fishRepository.findByFid(fid);
        //System.out.println(fish.toString());
        return fish;
    }
}
