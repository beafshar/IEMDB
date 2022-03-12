package com.myservlet.Model.Error;

public class UserNotFound extends Exception {
    public UserNotFound() {
        super("UserNotFound");
    }
}
