package com.example.boxscores.model;

import com.example.boxscores.enums.Positions;
import com.example.boxscores.enums.WNBATeams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Document(collection = "playerdetails")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDetails {

    @Id
    @JsonProperty("PlayerID")
    private String playerID;

    @Field("FirstName")
    @JsonProperty("FirstName")
    private String firstName;

    @Field("LastName")
    @JsonProperty("LastName")
    private String lastName;

    @Field("WNBATeamName")
    @JsonProperty("WNBATeamName")
    private WNBATeams WNBATeamName;

    @Field("UnrivaledTeamName")
    @JsonProperty("UnrivaledTeamName")
    private String unrivaledTeamName;

    @Field("Position")
    @JsonProperty("Position")
    private Positions position;

    @Field("JerseyNumber")
    @JsonProperty("JerseyNumber")
    private String jerseyNumber;

    @Field("Height")
    @JsonProperty("Height")
    private String height;

    @Field("Age")
    @JsonProperty("Age")
    private String age;

    @Field("BirthDate")
    @JsonProperty("BirthDate")
    private String birthDate;

    @Field("College")
    @JsonProperty("College")
    private String college;

    @Field("ImageURL")
    @JsonProperty("ImageURL")
    private String imageURL;

    @Field("SocialLinks")
    @JsonProperty("SocialLinks")
    private SocialLinks socialLinks;

}
