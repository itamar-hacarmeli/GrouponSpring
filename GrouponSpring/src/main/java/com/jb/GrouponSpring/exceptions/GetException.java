package com.jb.GrouponSpring.exceptions;


/**
 * GetException class handle runtime errors related to get methods.
 */
public class GetException extends Exception{
    public GetException() {
    }

    public GetException(String message) {
        super(message);
    }
}
