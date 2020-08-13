package com.poppulo.entity;

import java.util.Date;
import java.util.List;

public class Line {
    private String id;
    private List<Character> elements;
    private Float score;
    private Date createdAt;
    private Date updatedAt;

    public Line() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Character> getElements() {
        return elements;
    }

    public void setElements(List<Character> elements) {
        this.elements = elements;
    }

    public Float getScore() {
        if(score == null) {
            this.computeScore();
        }
        return score;
    }

    public void computeScore() {
        // Function to compute line score
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "LineDTO{" +
                "id='" + id + '\'' +
                ", elements=" + elements +
                ", score=" + score +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
