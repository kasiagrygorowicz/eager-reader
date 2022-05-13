package com.example.eagerreader.app.exception.bookException;

public class DuplicateBookException extends RuntimeException{
    public DuplicateBookException(String message) {
        super(message);
    }
}
