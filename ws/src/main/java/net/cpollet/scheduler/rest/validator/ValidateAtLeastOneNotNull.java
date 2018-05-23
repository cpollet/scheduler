package net.cpollet.scheduler.rest.validator;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateAtLeastOneNotNull.ValidateAtLeastOneNotNullValidator.class)
public @interface ValidateAtLeastOneNotNull {
    String message() default "net.cpollet.scheduler.rest.validator.ValidateAtLeastOneNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields();

    class ValidateAtLeastOneNotNullValidator implements ConstraintValidator<ValidateAtLeastOneNotNull, Object> {
        private String[] fieldNames;

        public void initialize(ValidateAtLeastOneNotNull constraintAnnotation) {
            this.fieldNames = constraintAnnotation.fields();
        }

        public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
            return Arrays.stream(fieldNames)
                    .map(name -> {
                        try {
                            return PropertyUtils.getProperty(object, name);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            return null;
                        }
                    })
                    .anyMatch(Objects::nonNull);
        }
    }
}
