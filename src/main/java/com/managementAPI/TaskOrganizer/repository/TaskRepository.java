package com.managementAPI.TaskOrganizer.repository;

import com.managementAPI.TaskOrganizer.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT task FROM Task task WHERE task.status=:status")
    List<Task> findAllByStatus(@Param("status") String status);
}
