package org.example.blocklygames.model;

import lombok.Data;

@Data
public class MapGame {
    private Integer id;
    private Integer diff;
    private String info;
    private String mapInfo;
    private String toolbox;
    private Integer needCoin;
    private Integer firstDegree;
    private Integer minStep;
    private Integer minBlock;
}
