package com.example.tallerredes.dtos;

public class SignInDto {

    public String Email;
    public String Password;

    public SignInDto() {
    }

    public SignInDto(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public SignInDto setEmail(String email) {
        Email = email;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public SignInDto setPassword(String password) {
        Password = password;
        return this;
    }
}
