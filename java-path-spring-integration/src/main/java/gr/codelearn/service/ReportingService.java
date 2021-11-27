package gr.codelearn.service;


import org.springframework.messaging.Message;

import java.util.Map;

public interface ReportingService {
    Map<String, Object> executeReports(Message message);
}
