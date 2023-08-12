package com.example.AndroidBack.Repository;


import com.example.AndroidBack.Model.FishRope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRopeRepository extends JpaRepository<FishRope, Long> {

    FishRope findByFrid(Long frid);
}
