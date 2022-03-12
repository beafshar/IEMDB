package com.myservlet.Model.Error;

public class MovieNotFound extends Exception {
    public MovieNotFound() {
        super("MovieNotFound");
    }
}