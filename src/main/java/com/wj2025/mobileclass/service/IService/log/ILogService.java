package com.wj2025.mobileclass.service.IService.log;

import com.wj2025.mobileclass.model.log.LogModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ILogService extends JpaRepository<LogModel,Long> {
    List<LogModel> findByClassification(String classification, Pageable pageable);
    List<LogModel> findByTitleContaining(String title, Pageable pageable);
    List<LogModel> findByContentContaining(String content, Pageable pageable);
    List<LogModel> findByTitleContainingAndContentContaining(String title, String content, Pageable pageable);
    List<LogModel> findByDateTimeBetween(Date start, Date end, Pageable pageable);
}
