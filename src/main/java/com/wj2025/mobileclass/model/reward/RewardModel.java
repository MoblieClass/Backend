package com.wj2025.mobileclass.model.reward;

import jakarta.persistence.*;

@Entity
@Table(name = "xw05_reward")
public class RewardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String start_date;
    private String end_date;
    private boolean is_finished;
    private int bonus;
    private int creator_id;

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
    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    public boolean isIs_finished() {
        return is_finished;
    }
    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }
    public int getBonus() {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public int getCreator_id() {
        return creator_id;
    }
    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }
}
