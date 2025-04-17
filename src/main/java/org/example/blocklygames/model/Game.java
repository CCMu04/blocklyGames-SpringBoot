package org.example.blocklygames.model;

import lombok.Data;

@Data
public class Game {
    private Integer id;
    private String name;
    private String english;
    private String info;
    private Integer display;
}
