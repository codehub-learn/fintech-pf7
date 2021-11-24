package gr.codelearn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// AMQP requires serializable
public class Payment implements Serializable {
    private UUID id;
    private Account creditor;
    private Account debtor;
    private Date valueDate;
    private BigDecimal paymentAmount;
    private Currency paymentCurrency;
    // todo should this be defined in here?
    private BigDecimal feeAmount;
    private Currency feeCurrency;
}
