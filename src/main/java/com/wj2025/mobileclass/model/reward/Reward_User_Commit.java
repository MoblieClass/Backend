package com.wj2025.mobileclass.model.reward;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "xw05_reward_user_commit")
public class Reward_User_Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int rewardId;
    private int userId;
    private LocalDateTime createdAt;
    private String content;
    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int reward_id) {
        this.rewardId = reward_id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int user_id) {
        this.userId = user_id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
