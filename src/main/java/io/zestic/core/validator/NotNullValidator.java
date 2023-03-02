package io.zestic.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import io.zestic.core.annotation.NotNull;

public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    return value != null ? true : false;
  }
}
