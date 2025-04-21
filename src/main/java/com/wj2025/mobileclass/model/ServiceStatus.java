package com.wj2025.mobileclass.model;

import java.util.Date;

public class ServiceStatus {
    private String status;
    private Date date;

    public ServiceStatus(){}
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public ServiceStatus(String status){
        this.status = status;
        this.date = new Date();
    }
    public Date getDate() {
        return date;
    }
}
