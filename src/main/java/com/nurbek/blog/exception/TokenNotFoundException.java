package com.nurbek.blog.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {

      super(message);
    }
}
