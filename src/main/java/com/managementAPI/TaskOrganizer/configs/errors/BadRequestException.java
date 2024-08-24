package com.managementAPI.TaskOrganizer.configs.errors;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
