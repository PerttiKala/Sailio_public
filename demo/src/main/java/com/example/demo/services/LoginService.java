package com.example.demo.services;

import com.example.demo.models.UserModel;

public class LoginService {

    public UserModel getCurrentUser() {
        return currentUserModel;
    }

    private UserModel currentUserModel;
    public boolean validateLoginCredentials(String username, String password) {
        return username.length() >= 3 && password.length() >= 3;
    }
    public UserModel login(String username, String password) {
        this.currentUserModel = new UserModel(username, password);
        return this.currentUserModel;
    }

    public boolean isUserLoggedIn() {
        return this.currentUserModel != null;
    }

    // Logout user
    public void logout() {
        this.currentUserModel = null;
    }
}
