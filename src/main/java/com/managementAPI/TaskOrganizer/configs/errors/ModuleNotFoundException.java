package com.managementAPI.TaskOrganizer.configs.errors;

public class ModuleNotFoundException extends RuntimeException{
    public ModuleNotFoundException(String message) {
        super(message);
    }
}
