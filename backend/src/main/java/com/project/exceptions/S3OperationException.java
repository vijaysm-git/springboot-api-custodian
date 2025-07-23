package com.project.exceptions;

public class S3OperationException extends RuntimeException {
    public S3OperationException(String message, Throwable cause) {
        super(message, cause);
    }
    public S3OperationException(String message) {
        super(message);
    }
}