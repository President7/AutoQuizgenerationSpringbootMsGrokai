package com.billion.quiz.collections;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    private String id;
    private String name;
    @Field("quiz_title")
    private String title;
    private String description;
    private Integer maxMarks;
    private Integer timeLimit;
    private String createdBy;
    private Integer noOfQuestions;
    private String imageUrl;
    private boolean live;
    private Integer passingMark;
    private String categoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Integer getPassingMark() {
        return passingMark;
    }

    public void setPassingMark(Integer passingMark) {
        this.passingMark = passingMark;
    }

    public String getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}



