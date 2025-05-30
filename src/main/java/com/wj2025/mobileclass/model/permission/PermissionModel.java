package com.wj2025.mobileclass.model.permission;

import jakarta.persistence.*;

@Entity
@Table(name = "xw05_permission")
public class PermissionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title; // 权限称号
    private String description;

    @Column(unique = true)
    private String permissionName; // 内部权限名，最原子权限，唯一

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
    public String getPermissionName() {
        return permissionName;
    }
    public void setPermissionName(String permissions) {
        this.permissionName = permissions;
    }
}
