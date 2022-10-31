package com.internship.pairist.repository;

import com.internship.pairist.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
