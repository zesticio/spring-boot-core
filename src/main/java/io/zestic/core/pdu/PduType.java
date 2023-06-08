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

package io.zestic.core.pdu;

import java.util.HashMap;
import java.util.Map;
import io.zestic.core.util.HTTPErrorCodes;

/**
 * Error code for USER module.
 */
public enum PduType {

  TEXT_MESSAGE(0x100000, "Test Message"),
  OBJECT_MESSAGE(0x100001, "Object Message");

  private static final Map<Integer, HTTPErrorCodes> LOOKUP = new HashMap<Integer, HTTPErrorCodes>();

  static {
    for (final HTTPErrorCodes enumeration : HTTPErrorCodes.values()) {
      LOOKUP.put(enumeration.getCode(), enumeration);
    }
  }

  private final Integer code;

  private final String message;

  private PduType(final Integer code, final String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
