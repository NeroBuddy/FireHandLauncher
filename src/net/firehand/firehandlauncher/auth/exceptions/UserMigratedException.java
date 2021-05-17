package net.firehand.firehandlauncher.auth.exceptions;

import net.firehand.firehandlauncher.auth.responses.ErrorResponse;

public class UserMigratedException extends RequestException{

    public UserMigratedException(ErrorResponse error) { super(error); }

}
