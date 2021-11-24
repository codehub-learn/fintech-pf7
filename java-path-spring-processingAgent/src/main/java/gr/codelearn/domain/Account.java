package gr.codelearn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
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
    private String name;
    private String iban;
    private AccountType type;
}
