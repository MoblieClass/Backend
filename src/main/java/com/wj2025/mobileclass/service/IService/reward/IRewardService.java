package com.wj2025.mobileclass.service.IService.reward;

import com.wj2025.mobileclass.model.reward.RewardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRewardService extends JpaRepository<RewardModel,Long> {
}
