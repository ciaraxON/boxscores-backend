// java
package com.example.boxscores.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameFilters {
    private List<String> gameTypes;
    private List<String> seasons;
    private List<String> opponents;
}
