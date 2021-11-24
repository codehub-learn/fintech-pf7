package gr.codelearn.validation;

import gr.codelearn.domain.Currency;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


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
