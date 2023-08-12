package com.example.AndroidBack.Controller;

import com.example.AndroidBack.Model.FishBait;
import com.example.AndroidBack.Model.FishContest;
import com.example.AndroidBack.Model.FishInfo;
import com.example.AndroidBack.Model.FishRope;
import com.example.AndroidBack.Service.FishBaitService;
import com.example.AndroidBack.Service.FishContestService;
import com.example.AndroidBack.Service.FishRopeService;
import com.example.AndroidBack.Service.FishService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/fish/*")
@AllArgsConstructor
public class FishController {

    FishService fishService;
    FishBaitService fishBaitService;
    FishContestService fishContestService;
    FishRopeService fishRopeService;

    @GetMapping("fishlist")
    public List<FishInfo> getFishList() {
        List<FishInfo> fishes = fishService.getFishList();
        System.out.println(fishes.toString());
        return fishes;
    }

    @GetMapping("fishinfo")
    public FishInfo getFish(@RequestParam("fid") Long fid) {
        FishInfo fish = fishService.getFish(fid);
        System.out.println(fish.toString());
        return fish;
    }

    @GetMapping("fishropelist")
    public List<FishRope> getRopeList() {
        return fishRopeService.getRopeList();
    }

    @GetMapping("fishropedetail")
    public FishRope getRopeDetail(@RequestParam("frid") Long frid) {
        return fishRopeService.getRope(frid);
    }

    @GetMapping("fishbaitlist")
    public List<FishBait> getBaitList() {
        return fishBaitService.getBaitList();
    }

    @GetMapping("fishbaitdetail")
    public FishBait getBaitDetail(@RequestParam("fbid") Long fbid) {
        return fishBaitService.getBait(fbid);
    }

    @GetMapping("fishcontestlist")
    public List<FishContest> getContestList() {
        return fishContestService.getContestList();
    }

    @GetMapping("fishcontestdetail")
    public FishContest getContestDetail(@RequestParam("fcid") Long fcid) {
        return fishContestService.getContest(fcid);
    }
}
