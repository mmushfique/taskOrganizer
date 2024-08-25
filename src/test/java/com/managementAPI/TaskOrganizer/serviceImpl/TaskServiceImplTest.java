package com.managementAPI.TaskOrganizer.serviceImpl;

import com.managementAPI.TaskOrganizer.domain.Task;
import com.managementAPI.TaskOrganizer.domain.TaskStatus;
import com.managementAPI.TaskOrganizer.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    public void setup(){
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("This is a test task");
        task.setStatus("Pending");
        task.setDueDateTime(Instant.now());
    }

    @Test
    public void saveTaskTest(){
        // Precondition
        given(taskRepository.save(task)).willReturn(task);

        // Action
        Task savedTask = taskService.save(task);

        // Verify the output
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getStatus()).isEqualTo(TaskStatus.PENDING.toString());
    }

    @Test
    public void getTaskByIdTest(){
        // Precondition
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        // Action
        Task foundTask = taskService.findOne(task.getId());

        // Verify
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(1L);
    }

    @Test
    public void getAllTasksTest(){
        Task task2 = new Task();
        task2.setId(1L);
        task2.setTitle("Test Task2");
        task2.setDescription("This is a test task2");
        task2.setStatus("Pending");
        task2.setDueDateTime(Instant.now());

        // Precondition
        given(taskRepository.findAll()).willReturn(Arrays.asList(task,task2));

        // Action
        List<Task> taskList = taskService.findAll();

        // Verify
        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(2);
    }

    @Test
    public void getTasksByStatusTest(){
        // Precondition
        given(taskRepository.findAllByStatus(TaskStatus.PENDING.toString())).willReturn(Arrays.asList(task));

        // Action
        List<Task> tasksByStatus = taskService.findAllByStatus(TaskStatus.PENDING.toString());

        // Verify
        assertThat(tasksByStatus).isNotNull();
        assertThat(tasksByStatus.size()).isEqualTo(1);
    }

    @Test
    public void updateTaskTest() {
        // Arrange
        Task taskUpdated= task;
        taskUpdated.setTitle("Updated Task");
        taskUpdated.setDescription("Updated description");

        when(taskRepository.save(task)).thenReturn(taskUpdated);

        task.setTitle("Updated Task");
        task.setDescription("Updated description");
        Task updatedTask = taskService.save(task);

        // Assert
        assertThat(updatedTask.getTitle()).isEqualTo("Updated Task");
        assertThat(updatedTask.getDescription()).isEqualTo("Updated description");
    }

    @Test
    public void deleteTaskTest(){
        // Precondition
        willDoNothing().given(taskRepository).deleteById(task.getId());

        // Action
        taskService.delete(task.getId());

        // Verify
        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}
