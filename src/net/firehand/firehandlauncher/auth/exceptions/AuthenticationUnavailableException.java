package net.firehand.firehandlauncher.auth.exceptions;

import net.firehand.firehandlauncher.auth.responses.ErrorResponse;

public class AuthenticationUnavailableException extends Exception{

    public AuthenticationUnavailableException(ErrorResponse error) {

    }

}
