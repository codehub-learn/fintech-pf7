package gr.codelearn.validation;


import gr.codelearn.domain.Currency;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
@Documented
public @interface DateFormatted {

    String message() default "{message.key}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String pattern();

}
