package com.wj2025.mobileclass.model;

import java.util.Date;

public class LogModel {
    private String id;
    private String title;
    private String content;
    private Date date;
    private String classification;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }
}
