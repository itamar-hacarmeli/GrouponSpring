package com.jb.GrouponSpring.exceptions;


/**
 * GrouponException class handle runtime errors to maintain the normal flow of the application.
 */
public class GrouponException extends Exception {
    public GrouponException() {
        super("General exception");
    }

    public GrouponException(String massage) {
        super(massage);
    }
}
