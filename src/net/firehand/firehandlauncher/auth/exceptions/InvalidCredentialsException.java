package net.firehand.firehandlauncher.auth.exceptions;

import net.firehand.firehandlauncher.auth.responses.ErrorResponse;

public class InvalidCredentialsException extends RequestException{

    public InvalidCredentialsException(ErrorResponse error) { super(error); }

}
