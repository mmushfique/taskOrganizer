package com.managementAPI.TaskOrganizer.service;

import com.managementAPI.TaskOrganizer.domain.Task;

import java.util.List;

public interface TaskService {
    public Task save(Task task);
    public Task findOne(Long taskId) ;
    public List<Task> findAll();
    public void delete(Long taskId);
    public List<Task> findAllByStatus(String status);
}
