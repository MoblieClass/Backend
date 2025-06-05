package com.wj2025.mobileclass.controller.reward;

import com.wj2025.mobileclass.model.reward.RewardModel;
import com.wj2025.mobileclass.service.Service.PermissionService;
import com.wj2025.mobileclass.service.Service.RewardService;
import com.wj2025.mobileclass.service.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<?> getAllReward(
            @RequestParam(required = false) String title,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String comparison,
            @RequestParam(required = false) Boolean finished
    ) {
        if (comparison != null && !Set.of("gt", "lt").contains(comparison)) {
            return ResponseEntity.badRequest().build();
        }
        List<RewardModel> rewards = StringUtils.hasText(title)
                ? rewardService.getRewardByTitle(title)
                : rewardService.getRewards(page, size);

        Stream<RewardModel> rewardStream = rewards.stream();
        if (comparison != null) {
            LocalDateTime now = LocalDateTime.now();
            rewardStream = "gt".equals(comparison)
                    ? rewardStream.filter(r -> r.getEndDate().isAfter(now))
                    : rewardStream.filter(r -> r.getEndDate().isBefore(now) && !r.isFinished());
        }

        if (finished != null && finished) {
            rewardStream = rewardStream.filter(RewardModel::isFinished);
        }

        return ResponseEntity.ok(rewardStream.collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    @Operation(summary = "获取悬赏详情")
    public ResponseEntity<?> getReward(@PathVariable int id) {
        var reward = rewardService.getRewardById(id);
        if (reward == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reward);
    }

    @PostMapping("/add")
    @Operation(summary = "添加悬赏任务，需要权限 reward:create")
    public ResponseEntity<?> addReward(@RequestBody AddRewardRequest addRewardRequest) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (permissionService.checkHasPermission(currentUsername, "reward:create") || permissionService.checkHasPermission(currentUsername, "root")) {
            rewardService.addReward(addRewardRequest.title, addRewardRequest.description, addRewardRequest.startDate, addRewardRequest.endDate, addRewardRequest.isFinished, addRewardRequest.bonus, user.get().getId(),user.get().getUsername());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }


    @PostMapping("{id}")
    @Operation(summary = "修改悬赏任务，需要权限 reward:modify")
    public ResponseEntity<?> updateReward(
            @PathVariable int id,
            @RequestBody AddRewardRequest addRewardRequest) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var reward = rewardService.getRewardById(id);
        if (reward == null) {
            return ResponseEntity.notFound().build();
        }
        if (reward.getCreatorId() != user.get().getId() && !permissionService.checkHasPermission(currentUsername, "root")) {
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
                    reward.getCreatorId(),
                    reward.getCreatorName()
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
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var reward = rewardService.getRewardById(id);
        if (reward == null) {
            return ResponseEntity.notFound().build();
        }
        if (reward.getCreatorId() != user.get().getId() && !permissionService.checkHasPermission(currentUsername, "root")) {
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

    @PostMapping("/commit/{id}")
    @Operation(summary = "提交悬赏任务")
    public ResponseEntity<?> commitReward(@PathVariable int id, @RequestBody String content) {
        var reward = rewardService.getRewardById(id);
        if (reward == null) {
            return ResponseEntity.notFound().build();
        }
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (content.isEmpty()) {
            return ResponseEntity.badRequest().body("empty content");
        }
        if (reward.getEndDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("reward finished");
        }
        rewardService.addRewardUserCommit(id, user.get().getId(), content);
        return ResponseEntity.ok("success");
    }

    @GetMapping("{id}/my-commits")
    @Operation(summary = "获取用户提交的悬赏记录")
    public ResponseEntity<?> getMyCommitReward(@PathVariable int id) {
        var reward = rewardService.getRewardById(id);
        if (reward == null) {
            return ResponseEntity.notFound().build();
        }
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var commits = rewardService.getRewardUserCommitsByUserId(user.get().getId());
        commits = commits.stream().filter(commit -> commit.getRewardId()==id).collect(Collectors.toList());
        return ResponseEntity.ok(commits);
    }

    @GetMapping("{id}/all-commits")
    @Operation(summary = "获取悬赏任务的所有提交，需要权限 reward:read")
    public ResponseEntity<?> getAllCommitReward(@PathVariable int id) {
        var reward = rewardService.getRewardById(id);
        if (reward == null) {
            return ResponseEntity.notFound().build();
        }
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!permissionService.checkHasPermission(currentUsername, "reward:read")&&!permissionService.checkHasPermission(currentUsername, "root")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var rewards_commits = rewardService.getRewardUserCommitsByRewardId(id);
        return ResponseEntity.ok(rewards_commits);
    }

    @DeleteMapping("/commit/{id}")
    @Operation(summary = "删除提交的悬赏")
    public ResponseEntity<?> deleteCommitReward(@PathVariable int id) {
        var rewardCommit = rewardService.getRewardUserCommitById(id);
        if (rewardCommit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUserByUsername(currentUsername);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (rewardCommit.get().getUserId() != user.get().getId() && !permissionService.checkHasPermission(currentUsername, "root")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
        }
        rewardService.removeRewardUserCommit(id);
        return ResponseEntity.ok("success");
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
