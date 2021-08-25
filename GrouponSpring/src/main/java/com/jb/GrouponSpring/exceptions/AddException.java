package com.jb.GrouponSpring.exceptions;


/**
 * AddException class handle runtime errors related to add methods.
 */
public class AddException extends Exception{
    public AddException() {
    }

    public AddException(String message) {
        super(message);
    }
}
