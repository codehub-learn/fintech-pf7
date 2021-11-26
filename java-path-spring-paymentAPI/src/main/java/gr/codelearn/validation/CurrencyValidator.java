package gr.codelearn.validation;

import gr.codelearn.domain.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;


/**
 * https://www.baeldung.com/javax-validations-enums
 */
public class CurrencyValidator implements ConstraintValidator<CurrencyType, Currency> {
    private Currency[] subset;

    @Override
    public void initialize(CurrencyType constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Currency value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
