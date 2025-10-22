package com.example.boxscores.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialLinks {

    @Field("Twitter")
    @JsonProperty("Twitter")
    private String twitter;

    @Field("Instagram")
    @JsonProperty("Instagram")
    private String instagram;

    @Field("Facebook")
    @JsonProperty("Facebook")
    private String facebook;

    @Field("TikTok")
    @JsonProperty("TikTok")
    private String tiktok;

    @Field("YouTube")
    @JsonProperty("YouTube")
    private String youtube;

    @Field("Website")
    @JsonProperty("Website")
    private String website;
}
