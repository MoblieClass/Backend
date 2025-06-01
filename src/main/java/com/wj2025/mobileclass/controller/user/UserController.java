package com.wj2025.mobileclass.controller.user;

import com.wj2025.mobileclass.model.user.UserModel;
import com.wj2025.mobileclass.service.Service.PermissionService;
import com.wj2025.mobileclass.service.Service.UserService;
import com.wj2025.mobileclass.utils.CacheUtils;
import com.wj2025.mobileclass.utils.EmailUtils;
import com.wj2025.mobileclass.utils.Sha256;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Tag(
        name = "用户管理",
        description = "用户管理相关接口"
)
public class UserController {
    private final UserService userService;
    private final PermissionService permissionService;
    private final EmailUtils emailUtils;
    public UserController(UserService userService, PermissionService permissionService, EmailUtils emailUtils) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.emailUtils = emailUtils;
    }

    @GetMapping("/self")
    @Operation(summary = "查看自身信息")
    public ResponseEntity<?> Self(){
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUser);
        user.ifPresent(u->u.setPassword("hide"));
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/self")
    @Operation(summary = "修改自身资料")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO dto) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var self = userService.getUserByUsername(currentUser);

        if (self.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        var selfModel = self.get();
        // 更新字段（只更新非空字段）
        if (dto.getName() != null) selfModel.setName(dto.getName());
        if (dto.getAge() != null) selfModel.setAge(dto.getAge());
        if (dto.getAddress() != null) selfModel.setAddress(dto.getAddress());
        if (dto.getPhone() != null) selfModel.setPhone(dto.getPhone());
        if (dto.getEmail() != null) selfModel.setEmail(dto.getEmail());
        if (dto.getAvatar() != null) selfModel.setAvatar(dto.getAvatar());
        if (dto.getPassword() != null) selfModel.setPassword(dto.getPassword());

        userService.modifyUser(selfModel);
        selfModel.setPassword("hide");
        return ResponseEntity.ok(selfModel);
    }

    @GetMapping("{username}")
    @Operation(summary = "查看用户信息，返回简单信息，返回完整信息需要 user:read 权限")
    public ResponseEntity<?>userInfo(@PathVariable String username){
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(username);
        user.ifPresent(u->u.setPassword("hide"));
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(permissionService.checkHasPermission(currentUser,"user:read") || permissionService.checkHasPermission(currentUser,"root")){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.ok(new SimpleUserInfo(user.get()));
        }
    }

    @DeleteMapping("/logged")
    @Operation(summary = "注销账号")
    public ResponseEntity<?> logged(){
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUser);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userService.deleteUserByUsername(currentUser));
    }

    @GetMapping("/roles")
    @Operation(summary = "获取用户角色")
    public ResponseEntity<?>getUserRoles(@RequestParam String username){
        var user = userService.getUserByUsername(username);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var roles = permissionService.getUserRoles(username);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/forgetPassword")
    @Operation(summary = "忘记密码")
    public ResponseEntity<?> forgetPassword(@RequestParam ForgetPasswordRequest request){
        var findUser = userService.getUserByUsername(request.getUsername());
        if(findUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!findUser.get().getEmail().equals(request.getEmail())){
            return ResponseEntity.badRequest().body("error email");
        }
        var cache = CacheUtils.getInstance();
        var uid = UUID.randomUUID().toString();
        var result = emailUtils.sendMail(request.email, "[Mobile Class] - 重置密码请求","尊敬的"+request.username+"\n您好，您收到这份邮件是因为您在平台上发起了忘记密码请求，您的恢复码为 "+uid+" ,请在3分钟内输入后重置密码，如果不是您的操作，请忽略此邮件。");
        if(result){
            cache.setValue("forgetPasswd_"+request.username, uid,3);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "重置密码")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        var findUser = userService.getUserByUsername(request.getUsername());
        if(findUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var user = findUser.get();
        var cache = CacheUtils.getInstance();
        var uid = cache.getValue("forgetPasswd_"+request.username);
        if(uid==null || !uid.equals(request.uid)){
            return ResponseEntity.badRequest().body("error uid");
        }
        user.setPassword(request.getPassword());
        userService.modifyUser(user);
        return ResponseEntity.ok("success");
    }

    public static class SimpleUserInfo{
        public int id;
        public String username;
        public String name;
        public String email;
        public String avatar;

        public SimpleUserInfo(UserModel userModel){
            this.id = userModel.getId();
            this.username = userModel.getUsername();
            this.name = userModel.getName();
            this.email = userModel.getEmail();
            this.avatar = userModel.getAvatar();
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getAvatar() {
            return avatar;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class UserUpdateDTO {
        private String password;
        private String name;
        private Integer age;       // 使用包装类型可以为 null
        private String address;
        private String phone;
        private String email;
        private String avatar;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class ForgetPasswordRequest{
        private String username;
        private String email;

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class ResetPasswordRequest{
        private String username;
        private String password;
        private String uid;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
