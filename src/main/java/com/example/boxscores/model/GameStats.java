package com.example.boxscores.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Document(collection = "gamestats")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameStats {

    @Id
    @JsonProperty("gameID")
    private String gameID;

    @Indexed
    @Field("GameDate")
    @JsonProperty("GameDate")
    private String gameDate;

    @Indexed
    @Field("playerID")
    @JsonProperty("playerID")
    private String playerID;

    @Field("playerTeam")
    @JsonProperty("playerTeam")
    private String playerTeam;

    @Field("playerTeamPoints")
    @JsonProperty("playerTeamPoints")
    private String playerTeamPoints;

    @Field("opponentTeam")
    @JsonProperty("opponentTeam")
    private String opponentTeam;

    @Field("opponentTeamPoints")
    @JsonProperty("opponentTeamPoints")
    private String opponentTeamPoints;

    @Field("minutesPlayed")
    @JsonProperty("minutesPlayed")
    private String minutesPlayed;

    @Field("playerPoints")
    @JsonProperty("playerPoints")
    private String playerPoints;

    @Field("playerFG")
    @JsonProperty("playerFG")
    private String playerFG;

    @Field("player3PT")
    @JsonProperty("player3PT")
    private String player3PT;

    @Field("playerFT")
    @JsonProperty("playerFT")
    private String playerFT;

    @Field("playerRebounds")
    @JsonProperty("playerRebounds")
    private String playerRebounds;

    @Field("playerAssists")
    @JsonProperty("playerAssists")
    private String playerAssists;

    @Field("playerSteals")
    @JsonProperty("playerSteals")
    private String playerSteals;

    @Field("playerBlocks")
    @JsonProperty("playerBlocks")
    private String playerBlocks;

    @Field("playerTurnovers")
    @JsonProperty("playerTurnovers")
    private String playerTurnovers;

    @Field("playerFouls")
    @JsonProperty("playerFouls")
    private String playerFouls;

    @Field("gameType")
    @JsonProperty("gameType")
    private String gameType;

    @Field("season")
    @JsonProperty("season")
    private String season;

    @Field("fullGameVideo")
    @JsonProperty("fullGameVideo")
    private List<MediaLink> fullGameVideo;

    @Field("highlights")
    @JsonProperty("highlights")
    private List<MediaLink> highlights;

    @Field("interviews")
    @JsonProperty("interviews")
    private List<MediaLink> interviews;
}
