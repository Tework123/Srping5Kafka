package ru.tework.spring5micro.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String messageId;

    @Column(length = 255)
    private String name;

    public Task(String messageId, String name) {
        this.messageId = messageId;
        this.name = name;
    }

    public Task() {
    }

    public void setMessageID(String messageId) {
        this.messageId = messageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getName() {
        return name;
    }

}
