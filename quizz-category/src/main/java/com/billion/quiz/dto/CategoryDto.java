package com.billion.quiz.dto;

import jakarta.persistence.Id;
import lombok.*;


public class CategoryDto {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean active;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CategoryDto(String id, String title, String description, boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.active = active;
    }

    public CategoryDto() {
    }
}
