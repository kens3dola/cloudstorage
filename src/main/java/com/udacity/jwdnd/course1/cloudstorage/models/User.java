package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class User {
    private int userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;
}
