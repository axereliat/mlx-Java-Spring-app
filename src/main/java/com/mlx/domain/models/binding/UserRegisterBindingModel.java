package com.mlx.domain.models.binding;

import javax.validation.constraints.NotEmpty;

public class UserRegisterBindingModel {

    @NotEmpty(message = "Username field is required.")
    private String username;

    @NotEmpty(message = "Password field is required.")
    private String password;

    private String confirmPassword;

    @NotEmpty(message = "Birthdate field is required.")
    private String birthdate;

    @NotEmpty(message = "Gender field is required.")
    private String gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
