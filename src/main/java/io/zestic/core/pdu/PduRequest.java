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

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class PduRequest<R extends PduResponse> extends Pdu {

    public PduRequest() {
    }

    /**
     * Create request PDU with given command id. Derived classes usually uses
     * <code>super(THE_COMMAND_ID)</code> where the <code>THE_COMMAND_ID</code>
     * is the command id of the PDU the derived class represents.
     */
    public PduRequest(int commandId) {
        super(commandId);
    }

    @JsonIgnore
    abstract public R createResponse();

    @JsonIgnore
    abstract public Class<R> getResponseClass();

    /**
     * Returns true. If the derived class cannot respond, then it must overwrite
     * this function to return false.
     */
    @Override
    public boolean canResponse() {
        return true;
    }

    /**
     * Returns true.
     */
    @Override
    public boolean isRequest() {
        return true;
    }

    /**
     * Returns false.
     */
    @Override
    public boolean isResponse() {
        return false;
    }

}
