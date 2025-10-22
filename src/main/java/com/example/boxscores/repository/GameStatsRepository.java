package com.example.boxscores.repository;

import com.example.boxscores.model.GameStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatsRepository extends MongoRepository<GameStats, String> {

    GameStats findFirstByPlayerIDOrderByGameDateDesc(String playerID);

    List<GameStats> findByPlayerIDOrderByGameDateDesc(String playerID);

}
