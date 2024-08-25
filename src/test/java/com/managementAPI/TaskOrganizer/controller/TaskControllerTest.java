package com.managementAPI.TaskOrganizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managementAPI.TaskOrganizer.domain.Task;
import com.managementAPI.TaskOrganizer.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;

    @BeforeEach
    public void setup() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("This is a test task");
        task.setStatus("Pending");
        task.setDueDateTime(Instant.now());
    }

    // Post Controller
    @Test
    public void saveTaskTest() throws Exception {
        task = new Task();
        task.setTitle("Test Task");
        task.setDescription("This is a test task");
        task.setStatus("Pending");
        task.setDueDateTime(Instant.now());

        // precondition
        given(taskService.save(any(Task.class))).willReturn(task);

        // action
        ResultActions response = mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        // verify
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.status", is(task.getStatus())));
    }

    // Get Controller
    @Test
    public void getAllTasksTest() throws Exception {
        Task task2 = new Task();
        task2.setId(1L);
        task2.setTitle("Test Task2");
        task2.setDescription("This is a test task2");
        task2.setStatus("Pending");
        task2.setDueDateTime(Instant.now());
        // precondition
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task);
        tasksList.add(task2);
        given(taskService.findAll()).willReturn(tasksList);

        // action
        ResultActions response = mockMvc.perform(get("/api/task"));

        // verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(tasksList.size())));
    }

    // Get by Id Controller
    @Test
    public void getTaskByIdTest() throws Exception {
        // precondition
        given(taskService.findOne(task.getId())).willReturn(task);

        // action
        ResultActions response = mockMvc.perform(get("/api/task/{id}", task.getId()));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.status", is(task.getStatus())));
    }

    // Update Task
    @Test
    public void updateTaskTest() throws Exception {
        Task task2 = new Task();
        task2.setId(1L);
        task2.setTitle("Test Task2");
        task2.setDescription("This is a test task2");
        task2.setStatus("Pending");
        task2.setDueDateTime(Instant.now());

        // Mock the behavior of the service layer
        given(taskService.save(any(Task.class))).willReturn(task2);

        // Act: Perform the PUT request
        ResultActions response = mockMvc.perform(put("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task2)));

        // Assert: Verify the response
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task2.getTitle())))
                .andExpect(jsonPath("$.description", is(task2.getDescription())))
                .andExpect(jsonPath("$.status", is(task2.getStatus())));
    }

    // Delete Task
    @Test
    public void deleteTaskTest() throws Exception {
        // precondition
        willDoNothing().given(taskService).delete(task.getId());

        // action
        ResultActions response = mockMvc.perform(delete("/api/task/{id}", task.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}