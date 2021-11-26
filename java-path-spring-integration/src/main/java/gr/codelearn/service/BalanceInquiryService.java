package gr.codelearn.service;

import java.util.Map;

public interface BalanceInquiryService {
    Map<String, Object> checkTransactionFinancially(Map<String, Object> payload);
}
