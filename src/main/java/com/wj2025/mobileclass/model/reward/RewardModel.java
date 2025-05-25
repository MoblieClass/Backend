package com.wj2025.mobileclass.model.reward;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "xw05_reward")
public class RewardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean isFinished;
    private int bonus;
    private int creatorId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date start_date) {
        this.startDate = start_date;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date end_date) {
        this.endDate = end_date;
    }
    public boolean isIsFinished() {
        return isFinished;
    }
    public void setIsFinished(boolean is_finished) {
        this.isFinished = is_finished;
    }
    public int getBonus() {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public int getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(int creator_id) {
        this.creatorId = creator_id;
    }
}
