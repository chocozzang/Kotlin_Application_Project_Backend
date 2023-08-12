package com.example.AndroidBack.Service;

import com.example.AndroidBack.Model.FishRope;
import com.example.AndroidBack.Repository.FishRopeRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FishRopeServiceImpl implements FishRopeService{

    FishRopeRepository fishRopeRepository;

    @Override
    public List<FishRope> getRopeList() {
        return fishRopeRepository.findAll();
    }

    @Override
    public FishRope getRope(Long frid) {
        return fishRopeRepository.findByFrid(frid);
    }
}
