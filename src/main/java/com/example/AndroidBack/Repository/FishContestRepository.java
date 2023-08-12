package com.example.AndroidBack.Repository;

import com.example.AndroidBack.Model.FishContest;
import com.example.AndroidBack.Model.FishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishContestRepository extends JpaRepository<FishContest, Long> {

    FishContest findByFcid(Long fcid);
}
