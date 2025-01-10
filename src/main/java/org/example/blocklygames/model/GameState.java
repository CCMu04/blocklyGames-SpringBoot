package org.example.blocklygames.model;

import lombok.Data;

@Data
public class GameState {
    private Integer id;
    private Integer diff;
    private Integer uid;
    private String status;
}
