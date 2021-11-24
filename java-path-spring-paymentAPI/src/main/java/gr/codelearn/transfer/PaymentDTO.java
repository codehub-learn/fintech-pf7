package gr.codelearn.transfer;

import gr.codelearn.domain.Currency;
import gr.codelearn.validation.CurrencyType;
import gr.codelearn.validation.DateFormatted;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Value
public class PaymentDTO {
    @NotNull
    String creditorName;
    // todo can we rename this to iban
    @Size(min = 16, max = 16, message = "Creditor Account should be of length 16.")
    @NotNull
    String creditorAccount;
    @NotNull
    String debtorName;
    // todo can we rename this to iban
    @Size(min = 16, max = 16, message = "Creditor Account should be of length 16.")
    @NotNull
    String debtorAccount;
    @Positive
    BigDecimal paymentAmount;
    @DateFormatted(pattern = "yyyyMMdd", message = "Date was not passed correctly")
            // todo does not 100% work, "-" are parsed if placed at particular places
    String valueDate;
    @CurrencyType(anyOf = {Currency.EUR}, message = "Must be any of accepted types (e.g., EUR)")
    Currency paymentCurrency;
    @Positive
    BigDecimal feeAmount;
    @CurrencyType(anyOf = {Currency.EUR}, message = "Must be any of accepted types (e.g., EUR)")
    Currency feeCurrency;
}
