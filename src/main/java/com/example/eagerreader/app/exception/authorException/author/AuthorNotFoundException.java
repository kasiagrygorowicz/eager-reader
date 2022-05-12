package com.example.eagerreader.app.exception.authorException.author;

public class AuthorNotFoundException extends RuntimeException{
    public  AuthorNotFoundException(String message) {
        super(message);
    }
}
