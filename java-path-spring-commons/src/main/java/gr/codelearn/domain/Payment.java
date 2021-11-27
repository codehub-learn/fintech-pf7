package gr.codelearn.domain;

import gr.codelearn.validation.CurrencyType;
import gr.codelearn.validation.DateFormatted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// AMQP requires serializable
public class Payment implements Serializable {
    @NotNull(message = "Creditor cannot be null.")
    private Account creditor;
    @NotNull(message = "Debtor cannot be null.")
    private Account debtor;
    @NotNull(message = "Value Date cannot be null.")
    @DateFormatted(pattern = "yyyyMMdd", message = "Date was not passed correctly.")
    // Does not 100% work, "-" are parsed if placed at particular places
    private Date valueDate;
    @NotNull(message = "Payment amount cannot be null.")
    @Positive(message = "Payment amount needs to be positive.")
    private BigDecimal paymentAmount;
    @CurrencyType(anyOf = {Currency.EUR}, message = "Must be any of the accepted types (e.g., EUR)")
    @NotNull(message = "Payment currency cannot be null.")
    private Currency paymentCurrency;
    @Positive(message = "Fee needs to be positive.")
    @NotNull(message = "Fee amount cannot be null.")
    private BigDecimal feeAmount;
    @CurrencyType(anyOf = {Currency.EUR}, message = "Must be any of the accepted types (e.g., EUR)")
    @NotNull(message = "Fee currency cannot be null.")
    private Currency feeCurrency;
}
