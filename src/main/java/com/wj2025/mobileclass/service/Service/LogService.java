package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.log.LogModel;
import com.wj2025.mobileclass.service.IService.log.ILogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogService {
    private final ILogService logService;
    public LogService(ILogService logService) {
        this.logService = logService;
    }

    public void Log(String title, String content, Date date, String classification) {
        var model = new LogModel();
        model.setTitle(title);
        model.setContent(content);
        model.setDate(date);
        model.setClassification(classification);
        logService.save(model);
    }

    public List<LogModel> getLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logService.findAll(pageable).getContent();
    }

    public List<LogModel> getLogsByClass(String classification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logService.findByClassification(classification,pageable);
    }

    public List<LogModel> getLogsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logService.findByTitleContaining(title,pageable);
    }

    public List<LogModel> getLogsByContent(String content, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logService.findByContentContaining(content,pageable);
    }

    public List<LogModel> getLogsByTitleAndContent(String title, String content,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logService.findByTitleContainingAndContentContaining(title, content,pageable);
    }

    public List<LogModel> getLogsByDateBetween(Date start, Date end,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logService.findByDateBetween(start, end,pageable);
    }
}
