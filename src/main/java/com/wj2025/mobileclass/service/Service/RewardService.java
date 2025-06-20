package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.reward.RewardModel;
import com.wj2025.mobileclass.model.reward.Reward_User_Commit;
import com.wj2025.mobileclass.service.IService.reward.IRewardService;
import com.wj2025.mobileclass.service.IService.reward.IReward_User_CommitService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RewardService {
    private final IRewardService rewardService;
    private final IReward_User_CommitService reward_User_CommitService;
    public RewardService(IRewardService rewardService, IReward_User_CommitService reward_User_CommitService) {
        this.rewardService = rewardService;
        this.reward_User_CommitService = reward_User_CommitService;
    }

    public void addReward(String title, String description, LocalDateTime start_date, LocalDateTime end_date, boolean is_finished, String bonus, int creator_id,String creator_name) {
        var reward = new RewardModel();
        reward.setTitle(title);
        reward.setDescription(description);
        reward.setStartDate(start_date);
        reward.setEndDate(end_date);
        reward.setFinished(is_finished);
        reward.setBonus(bonus);
        reward.setCreatorId(creator_id);
        reward.setCreatorName(creator_name);
        rewardService.save(reward);
    }

    public void addRewardUserCommit(int reward_id, int user_id,String content) {
        var rewardUserCommit = new Reward_User_Commit();
        rewardUserCommit.setRewardId(reward_id);
        rewardUserCommit.setUserId(user_id);
        rewardUserCommit.setContent(content);
        rewardUserCommit.setCreatedAt(LocalDateTime.now());
        reward_User_CommitService.save(rewardUserCommit);
    }

    public List<RewardModel> getRewards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rewardService.findAll(pageable).getContent();
    }

    public RewardModel getRewardById(long id) {
        return rewardService.findById(id).orElse(null);
    }

    public List<RewardModel> getRewardByTitle(String title) {
        return rewardService.findAllByTitleContaining(title);
    }

    public List<RewardModel> getRewardByTitleAndDateAfter(String title,LocalDateTime end_date) {
        return rewardService.findAllByTitleContainingAndEndDateAfter(title,end_date);
    }
    public List<RewardModel> getRewardByTitleAndDateBefore(String title,LocalDateTime end_date) {
        return rewardService.findAllByTitleContainingAndEndDateBefore(title,end_date);
    }

    public Optional<Reward_User_Commit> getRewardUserCommitById(int id) {
        return reward_User_CommitService.findById((long)id);
    }

    public List<Reward_User_Commit> getRewardUserCommitsByRewardId(int reward_id) {
        return reward_User_CommitService.findByRewardId(reward_id);
    }

    public List<Reward_User_Commit> getRewardUserCommitsByUserId(int user_id) {
        return reward_User_CommitService.findByUserId(user_id);
    }

    public void removeReward(int id){
        rewardService.deleteById((long) id);
    }

    public void removeRewardUserCommit(int id){
        reward_User_CommitService.deleteById((long) id);
    }

    public RewardModel updateReward(int id, String title, String description, LocalDateTime startDate, LocalDateTime endDate, boolean isFinished, String bonus, int creator_id,String creator_name) {
        var reward = new RewardModel();
        reward.setId(id);
        reward.setTitle(title);
        reward.setDescription(description);
        reward.setStartDate(startDate);
        reward.setEndDate(endDate);
        reward.setFinished(isFinished);
        reward.setBonus(bonus);
        reward.setCreatorId(creator_id);
        reward.setCreatorName(creator_name);
        return rewardService.save(reward);
    }
}
