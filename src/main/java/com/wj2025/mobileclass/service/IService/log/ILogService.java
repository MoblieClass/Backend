package com.wj2025.mobileclass.service.IService.log;

import com.wj2025.mobileclass.model.log.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ILogService extends JpaRepository<LogModel,Long> {
    List<LogModel> findByClassification(String classification);
    List<LogModel> findByTitle(String title);
    List<LogModel> findByContent(String content);
    List<LogModel> findByTitleAndContent(String title, String content);
    List<LogModel> findByDateBetween(Date start, Date end);
}
