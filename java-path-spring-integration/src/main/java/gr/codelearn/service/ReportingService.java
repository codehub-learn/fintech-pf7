package gr.codelearn.service;


import org.springframework.messaging.Message;

import java.util.Map;

public interface ReportingService {
    // this service receives a message because the message might include other information that we might want to report
    Map<String, Object> executeReports(Message message);
}
