package com.studyLog4u.security.oauth2.exception;

public class OAuthProviderMissMatchException extends RuntimeException{
    public OAuthProviderMissMatchException(String message){
        super(message);
    }

    public OAuthProviderMissMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthProviderMissMatchException(Throwable cause) {
        super(cause);
    }

}
