package io.zestic.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import io.zestic.core.annotation.NotEmpty;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, Object> {

  private String message;

  @Override
  public void initialize(NotEmpty annotation) {
    this.message = annotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    return value != null ? (true) : false;
  }
}
