package com.example.boxscores.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaLink {

    @Field("url")
    @JsonProperty("url")
    private String url;

    @Field("title")
    @JsonProperty("title")
    private String title;

    @Field("provider")
    @JsonProperty("provider")
    private String provider;

    @Field("timestamp")
    @JsonProperty("timestamp")
    private String timestamp; // optional: "mm:ss" or ISO-8601
}
