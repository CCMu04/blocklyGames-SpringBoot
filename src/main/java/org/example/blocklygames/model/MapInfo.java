package org.example.blocklygames.model;

import lombok.Data;

@Data
public class MapInfo {
    private int diff;
    private String mapInfo;
    private String toolbox;
    private int needCoin;
    private int firstDegree;
    private int minStep;
    private int minBlock;
}
