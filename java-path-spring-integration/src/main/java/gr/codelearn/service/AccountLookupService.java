package gr.codelearn.service;


import java.util.Map;

public interface AccountLookupService {
    public Map<String, Object> validate(Map<String, Object> payload);
}
