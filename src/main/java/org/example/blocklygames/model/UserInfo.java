package org.example.blocklygames.model;

import lombok.Data;

@Data
public class UserInfo {
    private Integer uid;
    private String name;
    private Integer sex;
    private String phone;
    private String birthday;
    private String address;
}
