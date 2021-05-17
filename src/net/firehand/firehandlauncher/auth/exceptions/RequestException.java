package net.firehand.firehandlauncher.auth.exceptions;

import net.firehand.firehandlauncher.auth.responses.ErrorResponse;

public class RequestException extends Exception{

    private ErrorResponse error;
    public RequestException(ErrorResponse error) {
        this.error = error;
    }

    public ErrorResponse getError() {
        return error;
    }
    public String getErrorMessage(){
        return this.error.getErrorMessage();
    }
    public String getErrorCause(){
        return this.error.getCause();
    }

}
