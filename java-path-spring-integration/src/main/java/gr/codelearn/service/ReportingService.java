package gr.codelearn.service;

import java.util.Map;

public interface ReportingService {
    Map<String, Object> executeReports(Map<String, Object> payload);
}
