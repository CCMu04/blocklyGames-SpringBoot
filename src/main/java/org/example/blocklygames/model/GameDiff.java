package org.example.blocklygames.model;

import lombok.Data;

@Data
public class GameDiff {
    private Integer id;
    private Integer diff;
    private String info;
    private Integer score;
}
