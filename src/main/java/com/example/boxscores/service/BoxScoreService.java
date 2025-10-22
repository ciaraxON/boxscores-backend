package com.example.boxscores.service;

import com.example.boxscores.model.GameFilters;
import com.example.boxscores.model.GameStats;
import com.example.boxscores.model.PlayerDetails;
import com.example.boxscores.repository.PlayerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface BoxScoreService {
    PlayerDetails getPlayerDetailsByName(String firstName, String lastName, String playerID);
    PlayerDetails getPlayerDetailsByCombinedName(String combinedLower, String playerID);
    List<PlayerDetails> getPlayersByCombinedName(String combinedLower);
    List<PlayerDetails> getAllPlayers();
    GameStats getMostRecentGameForPlayer(String playerID);
    List<GameStats> getAllGamesForPlayer(String playerID);
    List<GameStats> getAllGamesForPlayer(String playerID, String gameType, String season, String opponentTeamName);
    GameFilters getAvailableFiltersForPlayer(String playerID);
}
