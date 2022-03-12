package com.myservlet.Model.Error;

public class InvalidCommand extends Exception {
    public InvalidCommand() {
        super("InvalidCommand");
    }
}

