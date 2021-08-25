package com.jb.GrouponSpring.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDetail class is used for GrouponCustomException class.
 * sometimes a status code is not enough to show the specifics of the error.
 * When needed, we can use the body of the response to provide the client with additional information.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String error;
    private String description;
}
