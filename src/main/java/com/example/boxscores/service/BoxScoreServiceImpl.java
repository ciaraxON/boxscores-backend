package com.example.boxscores.service.impl;

import com.example.boxscores.model.GameFilters;
import com.example.boxscores.model.GameStats;
import com.example.boxscores.model.PlayerDetails;
import com.example.boxscores.repository.GameStatsRepository;
import com.example.boxscores.repository.PlayerDetailsRepository;
import com.example.boxscores.service.BoxScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BoxScoreServiceImpl implements BoxScoreService {

    @Autowired
    private PlayerDetailsRepository playerDetailsRepository;

    @Autowired
    private GameStatsRepository gameStatsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<PlayerDetails> getAllPlayers() {
        return playerDetailsRepository.findAll();
    }

    @Override
    public PlayerDetails getPlayerDetailsByName(String firstName, String lastName, String playerID) {
        if (playerID != null && !playerID.trim().isEmpty()) {
            return playerDetailsRepository.findByPlayerID(playerID.trim());
        }
        List<PlayerDetails> matches = playerDetailsRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    public PlayerDetails getPlayerDetailsByCombinedName(String combinedLower, String playerID) {
        if (playerID != null && !playerID.trim().isEmpty()) {
            return playerDetailsRepository.findByPlayerID(playerID.trim());
        }
        List<PlayerDetails> matches = playerDetailsRepository.findByCombinedName(combinedLower.toLowerCase().trim());
        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    public List<PlayerDetails> getPlayersByCombinedName(String combinedLower) {
        return playerDetailsRepository.findByCombinedName(combinedLower.toLowerCase().trim());
    }

    @Override
    public GameStats getMostRecentGameForPlayer(String playerID) {
        if (playerID == null || playerID.trim().isEmpty()) {
            return null;
        }
        return gameStatsRepository.findFirstByPlayerIDOrderByGameDateDesc(playerID);
    }

    @Override
    public List<GameStats> getAllGamesForPlayer(String playerID) {
        if (playerID == null || playerID.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return gameStatsRepository.findByPlayerIDOrderByGameDateDesc(playerID);
    }

    @Override
    public List<GameStats> getAllGamesForPlayer(String playerID, String gameType, String season, String opponentTeamName) {
        if (playerID == null || playerID.trim().isEmpty()) {
            return Collections.emptyList();
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("playerID").is(playerID));

        if (gameType != null && !gameType.trim().isEmpty()) {
            Pattern p = Pattern.compile("^" + Pattern.quote(gameType.trim()) + "$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("gameType").regex(p));
        }

        if (season != null && !season.trim().isEmpty()) {
            Pattern p = Pattern.compile("^" + Pattern.quote(season.trim()) + "$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("season").regex(p));
        }

        if (opponentTeamName != null && !opponentTeamName.trim().isEmpty()) {
            Pattern p = Pattern.compile(Pattern.quote(opponentTeamName.trim()), Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("opponentTeam").regex(p));
        }

        query.with(Sort.by(Sort.Direction.DESC, "GameDate"));

        return mongoTemplate.find(query, GameStats.class);
    }

    @Override
    public GameFilters getAvailableFiltersForPlayer(String playerID) {
        if (playerID == null || playerID.trim().isEmpty()) {
            return new GameFilters(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }

        Query q = new Query();
        q.addCriteria(Criteria.where("playerID").is(playerID));

        List<String> gameTypes = mongoTemplate.findDistinct(q, "gameType", GameStats.class, String.class);
        List<String> seasons = mongoTemplate.findDistinct(q, "season", GameStats.class, String.class);
        List<String> opponents = mongoTemplate.findDistinct(q, "opponentTeam", GameStats.class, String.class);

        return new GameFilters(cleanList(gameTypes), cleanList(seasons), cleanList(opponents));
    }

    private List<String> cleanList(List<String> l) {
        if (l == null) return Collections.emptyList();
        return l.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }
}
