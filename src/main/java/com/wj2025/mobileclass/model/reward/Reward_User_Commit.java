package com.wj2025.mobileclass.model.reward;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "xw05_reward_user")
public class Reward_User_Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int reward_id;
    private int user_id;
    private Date created_at;
    private String content;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getReward_id() {
        return reward_id;
    }
    public void setReward_id(int reward_id) {
        this.reward_id = reward_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
