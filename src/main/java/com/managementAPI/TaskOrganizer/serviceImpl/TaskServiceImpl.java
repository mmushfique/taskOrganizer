package com.managementAPI.TaskOrganizer.serviceImpl;

import com.managementAPI.TaskOrganizer.configs.errors.ModuleNotFoundException;
import com.managementAPI.TaskOrganizer.domain.Task;
import com.managementAPI.TaskOrganizer.domain.TaskStatus;
import com.managementAPI.TaskOrganizer.repository.TaskRepository;
import com.managementAPI.TaskOrganizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Task save(Task task) {
        if(task.getStatus()==null)
            task.setStatus(TaskStatus.PENDING.toString());
        return taskRepository.save(task);
    }

    @Override
    public Task findOne(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()-> new ModuleNotFoundException("Task not found with id:" + id));
        return task;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByStatus(String status){
        return taskRepository.findAllByStatus(status);
    }
    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
