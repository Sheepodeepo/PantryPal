package com.PantryPal.dto;

public class UserReqBodyDto {
    private String name;
    private String email;
    private String password;


    public UserReqBodyDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserReqBodyDto(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
