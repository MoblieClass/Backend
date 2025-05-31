package com.wj2025.mobileclass.controller.permission;

import com.wj2025.mobileclass.service.Service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(
        name = "权限管理",
        description = "权限管理相关接口"
)
public class PermissionController {
    private final PermissionService permissionService;
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/roles")
    @Operation(summary = "获取全部角色")
    public ResponseEntity<?> getAllRoll(){
        return ResponseEntity.ok(permissionService.getAllRoles());
    }

    @GetMapping("/permissions")
    @Operation(summary = "获取全部权限")
    public ResponseEntity<?> getAllPermission(){
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @GetMapping("/roles/{roleName}/permissions")
    @Operation(summary = "获取角色权限")
    public ResponseEntity<?>getRolePermission(@PathVariable String roleName){
        var role = permissionService.getRole(roleName);
        if (role.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var permissions = permissionService.getPermissionByRoleId(role.get().getId());
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/permissions")
    @Operation(summary = "添加权限，需要权限 root")
    public ResponseEntity<?>addPermission(@RequestBody AddPermissionRequest permission){
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(currentUsername,"root")){
            return ResponseEntity.ok(permissionService.addPermission(permission.getTitle(),permission.getDescription(),permission.getPermissionName()));
        }else{
            return ResponseEntity.status(403).body("permission denied");
        }
    }

    @DeleteMapping("/permissions/{permissionName}")
    @Operation(summary = "删除权限，需要权限 root")
    public ResponseEntity<?> deletePermission(@PathVariable String permissionName) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (permissionService.checkHasPermission(currentUsername, "root")) {
            return ResponseEntity.ok(permissionService.deletePermission(permissionName));
        } else {
            return ResponseEntity.status(403).body("Permission denied");
        }
    }

    @PostMapping("/roles")
    @Operation(summary = "添加角色，需要权限 root")
    public ResponseEntity<?>addRole(@RequestBody AddRoleRequest role){
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(currentUsername,"root")){
            return ResponseEntity.ok(permissionService.addRole(role.name,role.description));
        }else{
            return ResponseEntity.status(403).body("permission denied");
        }
    }

    @DeleteMapping("/roles/{roleName}")
    @Operation(summary = "删除角色，需要权限 root")
    public ResponseEntity<?> deleteRole(@PathVariable String roleName) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (permissionService.checkHasPermission(currentUsername, "root")) {
            return ResponseEntity.ok(permissionService.deleteRole(roleName));
        } else {
            return ResponseEntity.status(403).body("Permission denied");
        }
    }

    @PostMapping("/roles/{roleName}/permissions")
    @Operation(summary = "添加权限到角色，需要权限 root")
    public ResponseEntity<?>addRolePermission(@PathVariable String roleName, @RequestParam String permissionName){
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(currentUsername, "root")){
            var role = permissionService.getRole(roleName);
            if(role.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            var permission = permissionService.getPermission(permissionName);
            if(permission.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(permissionService.addPermissionToRole(roleName,permissionName));
        }else{
            return ResponseEntity.status(403).body("permission denied");
        }
    }

    @DeleteMapping("/roles/{roleName}/permissions")
    @Operation(summary = "从角色中移除权限，需要权限 root")
    public ResponseEntity<?> removeRolePermission(@PathVariable String roleName, @RequestParam String permissionName) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (permissionService.checkHasPermission(currentUsername, "root")) {
            var role = permissionService.getRole(roleName);
            if (role.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var permission = permissionService.getPermission(permissionName);
            if (permission.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            boolean result = permissionService.deletePermissionFromRole(roleName, permissionName);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(403).body("Permission denied");
        }
    }

    public static class AddPermissionRequest {
        private String permissionName;
        private String description;
        private String title;
        public String getPermissionName() {
            return permissionName;
        }

        public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class AddRoleRequest {
        private String name;
        private String description;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
