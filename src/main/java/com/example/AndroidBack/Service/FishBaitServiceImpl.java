package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Repository.FishBaitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FishBaitServiceImpl implements FishBaitService{

    FishBaitRepository fishBaitRepository;

    @Override
    public List<FishBait> getBaitList() {
        return fishBaitRepository.findAll();
    }

    @Override
    public FishBait getBait(Long fbid) {
        return fishBaitRepository.findByFbid(fbid);
    }
}
