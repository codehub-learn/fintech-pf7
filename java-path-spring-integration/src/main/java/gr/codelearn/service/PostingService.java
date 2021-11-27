package gr.codelearn.service;


import java.util.Map;

public interface PostingService {
    Map<String, Object> makeTransaction(Map<String, Object> payload);
}
