package com.example.demo.controllers;

import com.example.demo.services.LoginService;

public class LoginController {

    LoginService loginService = new LoginService();

    // Implement login logic
    public boolean login(String username, String password) {
        if (loginService.validateLoginCredentials(username, password)) {
            // Implement user authentication logic here
            return loginService.login(username, password) != null;
        }
        return false;
    }

    // Implement login logic
    public void logout() {
        this.loginService.logout();
    }

}
