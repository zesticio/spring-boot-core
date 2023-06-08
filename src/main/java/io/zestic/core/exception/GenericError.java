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

package io.zestic.core.exception;

import lombok.Getter;

@Getter
public enum GenericError implements ApplicationError {

    ROK(0x000000, "Success"),
    RTE_METHOD_NOT_IMPL(0x800001, "Runtime exception, Method not implemented");

    private Integer code;
    private String message;

    GenericError(Integer code, String mesg) {
        this.code = code;
        this.message = mesg;
    }
}
