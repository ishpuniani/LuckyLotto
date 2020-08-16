package com.poppulo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket {
    private UUID id;
    private List<Line> lines;
    private Float totalScore;
    private boolean checked;
    private Date createdAt;
    private Date updatedAt;

    public Ticket() {
        lines = new ArrayList<Line>();
        checked = false;
        totalScore = 0F;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public void computeTotalScore() {
        this.totalScore = 0f;
        for(Line line : lines) {
            this.totalScore+=line.getScore();
        }
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", lines=" + lines +
                ", totalScore=" + totalScore +
                ", checked=" + checked +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
