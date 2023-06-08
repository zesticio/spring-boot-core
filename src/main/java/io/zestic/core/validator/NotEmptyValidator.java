/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
