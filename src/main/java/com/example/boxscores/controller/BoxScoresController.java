package com.example.boxscores.controller;

import com.example.boxscores.model.GameStats;
import com.example.boxscores.model.PlayerDetails;
import com.example.boxscores.model.GameFilters;
import com.example.boxscores.service.BoxScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin(origins = "https://boxscores-frontend.vercel.app/")
@RestController
public class BoxScoresController {

    @Autowired
    private BoxScoreService boxScoreService;

    // list all players
    @GetMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerDetails> getAllPlayers() {
        return boxScoreService.getAllPlayers();
    }

    //get player details by player FirstNameLastName
    @GetMapping(value = "/playerdetails/{playerName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerDetails> getPlayersByCombinedName(@PathVariable String playerName) {
        String combined = playerName.replaceAll("[-_+\\s]+", "").toLowerCase().trim();
        return boxScoreService.getPlayersByCombinedName(combined);
    }

    //get most recent Game Stats
    @GetMapping(value = "/player/{playerName}/latestgame", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameStats getMostRecentGame(@PathVariable("playerName") String playerName) {
        String playerID = resolvePlayerIdIfNeeded(playerName);
        GameStats latest = boxScoreService.getMostRecentGameForPlayer(playerID);
        if (latest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No recent game found for player " + playerID);
        }
        return latest;
    }

    //get all game stats for player with optional filters
    @GetMapping(value = "/player/{playerName}/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameStats> getAllGamesForPlayer(
            @PathVariable("playerName") String playerName,
            @RequestParam(required = false) String gameType,
            @RequestParam(required = false) String season,
            @RequestParam(name = "opponent", required = false) String opponentTeamName) {

        String playerID = resolvePlayerIdIfNeeded(playerName);
        return boxScoreService.getAllGamesForPlayer(playerID, gameType, season, opponentTeamName);
    }

    @GetMapping(value = "/player/{playerName}/games/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameFilters getFiltersForPlayer(@PathVariable("playerName") String playerName) {
        String playerID = resolvePlayerIdIfNeeded(playerName);
        return boxScoreService.getAvailableFiltersForPlayer(playerID);
    }

    // Helper: if the path variable looks like a combined name (not an opaque id), attempt to resolve to real playerID
    private String resolvePlayerIdIfNeeded(String raw) {
        if (raw == null) return null;
        String decoded = URLDecoder.decode(raw, StandardCharsets.UTF_8).trim();
        String candidate = decoded.replaceAll("[-_+\\s]+", "").toLowerCase();

        // if it's purely numeric, treat as an ID already
        if (candidate.matches("^\\d+$")) {
            return decoded;
        }

        // if it contains letters, try to resolve via the service (which returns a list)
        if (candidate.matches(".*[A-Za-z].*")) {
            List<PlayerDetails> matches = boxScoreService.getPlayersByCombinedName(candidate);
            if (matches != null && !matches.isEmpty()) {
                PlayerDetails pd = matches.get(0);
                if (pd != null && pd.getPlayerID() != null && !pd.getPlayerID().trim().isEmpty()) {
                    return pd.getPlayerID().trim();
                }
            }
        }

        // fallback to the decoded value
        return decoded;
    }
}
