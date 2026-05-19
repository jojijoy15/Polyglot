package com.problems.learning.system.lb;

/**
 * Thrown when no healthy servers are available to handle a request.
 */
public class NoHealthyServerException extends RuntimeException {

    public NoHealthyServerException(String message) {
        super(message);
    }
}

