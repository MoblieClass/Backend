package com.wj2025.mobileclass.controller.reward;

import com.wj2025.mobileclass.service.Service.PermissionService;
import com.wj2025.mobileclass.service.Service.RewardService;
import com.wj2025.mobileclass.service.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reward")
@Tag(
        name = "悬赏管理",
        description = "悬赏管理相关接口"
)
public class RewardController {
    private final RewardService rewardService;
    private final UserService userService;
    private final PermissionService permissionService;
    public RewardController(RewardService rewardService, UserService userService, PermissionService permissionService) {
        this.rewardService = rewardService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @GetMapping("/all")
    @Operation(summary = "获取全部悬赏")
    public ResponseEntity<?>getAllReward(@RequestParam int page, @RequestParam int size) {
        page-=1;
        if(page < 0 || size<=0 || size>100) {
            return ResponseEntity.badRequest().body("error parameter");
        }
        return ResponseEntity.ok(rewardService.getRewards(page,size));
    }

    @GetMapping("{id}")
    @Operation(summary = "获取悬赏详情")
    public ResponseEntity<?> getReward(@PathVariable int id) {
        var reward = rewardService.getRewardById(id);
        if(reward == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reward);
    }

    @PostMapping("/add")
    @Operation(summary = "添加悬赏任务，需要权限 reward:create")
    public ResponseEntity<?>addReward(@RequestBody AddRewardRequest addRewardRequest) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(permissionService.checkHasPermission(currentUsername,"reward:create")||permissionService.checkHasPermission(currentUsername,"root")){
            rewardService.addReward(addRewardRequest.title,addRewardRequest.description,addRewardRequest.startDate,addRewardRequest.endDate,addRewardRequest.isFinished,addRewardRequest.bonus,user.get().getId());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }


    @PostMapping("{id}")
    @Operation(summary = "修改悬赏任务，需要权限 reward:modify")
    public ResponseEntity<?> updateReward(
            @PathVariable Long id,
            @RequestBody AddRewardRequest addRewardRequest) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var reward = rewardService.getRewardById(id);
        if(reward == null) {
            return ResponseEntity.notFound().build();
        }
        if(reward.getCreatorId()!=user.get().getId()&&!permissionService.checkHasPermission(currentUsername, "root")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (permissionService.checkHasPermission(currentUsername, "reward:modify") ||
                permissionService.checkHasPermission(currentUsername, "root")) {
            var result = rewardService.updateReward(
                    id,
                    addRewardRequest.title,
                    addRewardRequest.description,
                    addRewardRequest.startDate,
                    addRewardRequest.endDate,
                    addRewardRequest.isFinished,
                    addRewardRequest.bonus,
                    user.get().getId()
            );
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除悬赏任务，需要权限 reward:delete")
    public ResponseEntity<?> deleteReward(@PathVariable int id) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var reward = rewardService.getRewardById(id);
        if(reward == null) {
            return ResponseEntity.notFound().build();
        }
        if(reward.getCreatorId()!=user.get().getId()&&!permissionService.checkHasPermission(currentUsername, "root")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (permissionService.checkHasPermission(currentUsername, "reward:delete") ||
                permissionService.checkHasPermission(currentUsername, "root")) {
            rewardService.removeReward(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
    }

    public static class AddRewardRequest {
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private boolean isFinished;
        private String bonus;

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

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public void setFinished(boolean finished) {
            isFinished = finished;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

    }
}
