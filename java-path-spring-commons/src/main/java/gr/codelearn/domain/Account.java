package gr.codelearn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
// AMQP requires serializable
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull(message = "Name cannot be null.")
    private String name;
    @Size(min = 16, max = 16, message = "IBAN should be of length 16.")
    @NotNull(message = "IBAN cannot be null.")
    private String iban;
    @NotNull(message = "Type cannot be null.")
    // This should also have a check for whether the type is acceptable or not
    private AccountType type;
    @PositiveOrZero(message = "Balance cannot be below zero.")
    private BigDecimal balance;
}
