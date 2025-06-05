package com.wj2025.mobileclass.service.IService.reward;

import com.wj2025.mobileclass.model.reward.RewardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IRewardService extends JpaRepository<RewardModel,Long> {
    List<RewardModel> findAllByTitleContaining(String title);
    List<RewardModel> findAllByTitleContainingAndEndDateAfter(String description, LocalDateTime endDate);
    List<RewardModel> findAllByTitleContainingAndEndDateBefore(String title, LocalDateTime endDate);
}
