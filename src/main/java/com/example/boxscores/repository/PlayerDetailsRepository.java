package com.example.boxscores.repository;

import com.example.boxscores.model.PlayerDetails;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDetailsRepository extends MongoRepository<PlayerDetails, String> {

    // find by PlayerID (used by service)
    PlayerDetails findByPlayerID(String playerID);

    // find by first and last name ignoring case (used by service)
    List<PlayerDetails> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    // find players where lower(concat(FirstName, LastName)) == provided value (e.g. "azzifudd")
    @Aggregation(pipeline = {
            "{ $project: { _id: 0, PlayerID: 1, FirstName: 1, LastName: 1, WNBATeamName: 1, UnrivaledTeamName: 1, Position: 1, JerseyNumber: 1, Height: 1, Age: 1, BirthDate: 1, College: 1, ImageURL: 1, SocialLinks: 1, combinedLower: { $toLower: { $concat: [\"$FirstName\", \"$LastName\"] } } } }",
            "{ $match: { combinedLower: ?0 } }",
            "{ $project: { _id: 0, PlayerID: 1, FirstName: 1, LastName: 1, WNBATeamName: 1, UnrivaledTeamName: 1, Position: 1, JerseyNumber: 1, Height: 1, Age: 1, BirthDate: 1, College: 1, ImageURL: 1, SocialLinks: 1 } }"
    })
    List<PlayerDetails> findByCombinedName(String combinedLower);
}
