package com.managementAPI.TaskOrganizer.controller;

import com.managementAPI.TaskOrganizer.configs.errors.BadRequestException;
import com.managementAPI.TaskOrganizer.domain.Task;
import com.managementAPI.TaskOrganizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping("/task")
    public ResponseEntity<Task> create(@Valid @RequestBody Task task){
        if(task.getId()!=null)
            throw new BadRequestException("A new Task cannot already have an id");
        Task result = taskService.save(task);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/task")
    public ResponseEntity<Task> update(@Valid @RequestBody Task task){
        if(task.getId()==null)
            throw new BadRequestException("Id not found");
        Task result = taskService.save(task);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        Task result = taskService.findOne(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/task")
    public List<Task> getAllTasks(){
        List<Task> result = taskService.findAll();
        return result;
    }

    @GetMapping("/task/filter-by-status")
    public List<Task> getAllTasksByStatus(@RequestParam String status){
        List<Task> result = taskService.findAllByStatus(status);
        return result;
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
