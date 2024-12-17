package org.example.blocklygames.model;

import lombok.Data;

@Data
public class RegisterInfo {
    private String username;
    private String password;
    private String name;
    private Integer sex;
    private String phone;
    private String birthday;
    private String address;
}
