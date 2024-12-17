package org.example.blocklygames.model;

import lombok.Data;

import java.util.List;

@Data
public class Dvo {
    private Integer code = 0;
    private String msg = "未知错误";
    private List<?> data = null;
}
