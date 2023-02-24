package in.zestic.core.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import in.zestic.core.validator.NotNullValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotNullValidator.class)
public @interface NotNull {

  //error message
  public String message() default "Value must not be null";

  //represents group of constraints
  public Class<?>[] groups() default {};

  //represents additional information about annotation
  public Class<? extends Payload>[] payload() default {};
}
