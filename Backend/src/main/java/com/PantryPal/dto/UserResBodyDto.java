package com.PantryPal.dto;

public class UserResBodyDto {
    public String email;

    public UserResBodyDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
