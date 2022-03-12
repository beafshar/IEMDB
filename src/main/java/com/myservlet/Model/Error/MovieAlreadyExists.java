package com.myservlet.Model.Error;

public class MovieAlreadyExists extends Exception {
    public MovieAlreadyExists() {
        super("MovieAlreadyExists");
    }
}
