package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.log.LogModel;
import com.wj2025.mobileclass.service.IService.log.ILogService;
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

    public List<LogModel> getLogs() {
        return logService.findAll();
    }

    public List<LogModel> getLogsByClass(String classification) {
        return logService.findByClassification(classification);
    }

    public List<LogModel> getLogsByTitle(String title) {
        return logService.findByTitle(title);
    }

    public List<LogModel> getLogsByContent(String content) {
        return logService.findByContent(content);
    }

    public List<LogModel> getLogsByTitleAndContent(String title, String content) {
        return logService.findByTitleAndContent(title, content);
    }

    public List<LogModel> getLogsByDateBetween(Date start, Date end) {
        return logService.findByDateBetween(start, end);
    }
}
