package com.jb.GrouponSpring.exceptions;


/**
 * UpdateException class handle runtime errors related to update methods.
 */
public class UpdateException extends Exception{
    public UpdateException() {
    }

    public UpdateException(String message) {
        super(message);
    }
}
