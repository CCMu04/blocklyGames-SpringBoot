package org.example.blocklygames.model;

import lombok.Data;

@Data
public class UserDetail {
    private Integer uid;
    private String username;
    private String name;
    private Integer sex;
    private String phone;
    private String birthday;
    private String address;
    private Integer type;
}
