package ru.tework.spring5micro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.tework.spring5micro.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByMessageId(String messageId);
}
