package com.wj2025.mobileclass.model.reward;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "xw05_reward")
public class RewardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean finished;
    private String bonus;
    private int creatorId;
    private String creatorName;

    public int getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime start_date) {
        this.startDate = start_date;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime end_date) {
        this.endDate = end_date;
    }
    public boolean isFinished() {
        return finished;
    }
    public void setFinished(boolean is_finished) {
        this.finished = is_finished;
    }
    public String getBonus() {
        return bonus;
    }
    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
    public int getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(int creator_id) {
        this.creatorId = creator_id;
    }

    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creator_name) {
        this.creatorName = creator_name;
    }
}
