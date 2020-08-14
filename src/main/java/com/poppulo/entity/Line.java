package com.poppulo.entity;

import com.poppulo.exception.LineException;
import com.poppulo.utils.LineUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Line {
    private String id;
    private List<Character> elements;
    private Float score;
    private Date createdAt;
    private Date updatedAt;

    public Line() {
    }

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
        if (score == null) {
            this.computeScore();
        }
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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

    /**
     * Function to generate ID of the line.
     */
    public void generateId() {
        if (this.elements == null) {
            throw new LineException("Cannot generate Line ID, elements empty");
        }
        this.id = elements.stream().map(String::valueOf).collect(Collectors.joining());
    }

    /**
     * Function to compute the score of a line.
     */
    public void computeScore() {
        if (elements == null || elements.size() < LineUtils.getLineSize()) {
            throw new LineException("Cannot compute line score. Invalid elements:: " + elements);
        }

        int n1 = Character.getNumericValue(elements.get(0));
        int n2 = Character.getNumericValue(elements.get(1));
        int n3 = Character.getNumericValue(elements.get(2));

        int result;
        if (n1 + n2 + n3 == 2) {
            result = 10;
        } else if (n1 == n2 && n2 == n3) {
            result = 5;
        } else if (n2 != n1 && n3 != n1) {
            result = 1;
        } else {
            result = 0;
        }

        this.score = (float) result;
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
