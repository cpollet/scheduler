package net.cpollet.scheduler.rest.validator;

import net.cpollet.scheduler.engine.api.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidJobTypeName.ValidJobTypeNameValidator.class)
public @interface ValidJobTypeName {
    String message() default "{net.cpollet.scheduler.rest.validator.ValidJobTypeName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ValidJobTypeNameValidator implements ConstraintValidator<ValidJobTypeName, String> {
        private final Scheduler scheduler;

        @Autowired
        public ValidJobTypeNameValidator(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return scheduler.validJobTypes().contains(value);
        }
    }
}
