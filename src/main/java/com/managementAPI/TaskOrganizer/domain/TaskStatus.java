package com.managementAPI.TaskOrganizer.domain;

public enum TaskStatus {
    PENDING("Pending"), IN_PROGRESS("In Progress"), COMPLETED("Completed"), CANCELLED("Cancelled"),OVERDUE("Over due");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
