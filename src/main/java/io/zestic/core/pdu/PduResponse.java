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

import com.fasterxml.jackson.annotation.JsonProperty;

public class PduResponse extends Pdu {

    @JsonProperty("result")
    private String result = Constants.STATUS_MESSAGE_MAP.get(Constants.STATUS_OK);

    public PduResponse() {
    }

    public PduResponse(int commandId) {
        super(commandId);
    }

    @Override
    public boolean isRequest() {
        return false;
    }

    @Override
    public boolean isResponse() {
        return true;
    }

    /**
     * Returns false as there can be no response to a response.
     */
    @Override
    public boolean canResponse() {
        return false;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}