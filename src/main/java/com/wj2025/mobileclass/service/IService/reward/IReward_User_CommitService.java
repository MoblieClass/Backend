package com.wj2025.mobileclass.service.IService.reward;

import com.wj2025.mobileclass.model.reward.Reward_User_Commit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReward_User_CommitService extends JpaRepository<Reward_User_Commit,Long> {
    List<Reward_User_Commit> findByreward_id(int rewardId, Pageable pageable);
    List<Reward_User_Commit> findByuser_id(int userId, Pageable pageable);
}
