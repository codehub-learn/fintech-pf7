package gr.codelearn.validation;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.domain.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DateFormatValidator extends AbstractLogEntity implements ConstraintValidator<DateFormatted, String> {

    private String pattern;

    @Override
    public void initialize(DateFormatted constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(object);
            return true;
        } catch (Exception e) {
            logger.info("Error occured during date validation: {}", e.getMessage());
            return false;
        }
    }
}